package com.example.F1StatsPlatform.sync;

import com.example.F1StatsPlatform.client.JolpicaApiClient;
import com.example.F1StatsPlatform.client.OpenF1ApiClient;
import com.example.F1StatsPlatform.client.dto.JolpicaDto;
import com.example.F1StatsPlatform.client.dto.OpenF1SessionDto;
import com.example.F1StatsPlatform.entity.*;
import com.example.F1StatsPlatform.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DataSyncService {

    private final JolpicaApiClient jolpicaApiClient;
    private final OpenF1ApiClient openF1ApiClient;

    private final RaceRepository raceRepository;
    private final RaceResultRepository raceResultRepository;
    private final DriverRepository driverRepository;
    private final TeamRepository teamRepository;

    public void syncAll(int season) {
        syncSchedule(season);
        syncOpenF1Timestamps(season);
        syncCompletedRaceResults(season);
        recalculateDriverStats(season);
    }


    @Transactional
    public void syncSchedule(int season) {
        List<JolpicaDto.Race> apiRaces = jolpicaApiClient.fetchSchedule(season);

        for (JolpicaDto.Race apiRace : apiRaces) {
            String externalRef = season + "-" + apiRace.getRound();

            Race race = raceRepository.findByExternalRef(externalRef)
                    .orElseGet(() -> Race.builder().externalRef(externalRef).build());

            race.setSeason(season);
            race.setRound(Integer.parseInt(apiRace.getRound()));
            race.setName(apiRace.getRaceName());

            if (apiRace.getCircuit() != null) {
                race.setCircuitName(apiRace.getCircuit().getCircuitName());
                if (apiRace.getCircuit().getLocation() != null) {
                    race.setCountry(apiRace.getCircuit().getLocation().getCountry());
                }
            }

            race.setRaceDate(LocalDate.parse(apiRace.getDate()));

            LocalDate raceDate = LocalDate.parse(apiRace.getDate());
            LocalDate today = LocalDate.now();
            if (raceDate.isBefore(today) || raceDate.isEqual(today)) {
                race.setStatus(RaceStatus.COMPLETED);
            } else {
                race.setStatus(RaceStatus.UPCOMING);
            }

            raceRepository.save(race);
        }

    }


    @Transactional
    public void syncOpenF1Timestamps(int season) {
        List<OpenF1SessionDto> sessions = openF1ApiClient.fetchRaceSessions(season);

        for (OpenF1SessionDto session : sessions) {
            if (session.getMeetingName() == null || session.getDateStart() == null) continue;

            raceRepository.findAllBySeasonOrderByRoundAsc(season).stream()
                    .filter(r -> namesMatch(r.getName(), session.getMeetingName()))
                    .findFirst()
                    .ifPresent(race -> {
                        race.setSessionStartUtc(OffsetDateTime.parse(session.getDateStart()));
                        if (session.getDateEnd() != null) {
                            race.setSessionEndUtc(OffsetDateTime.parse(session.getDateEnd()));
                        }
                        raceRepository.save(race);
                    });
        }

    }



    private boolean namesMatch(String raceName, String meetingName) {
        if (raceName == null || meetingName == null) return false;
        return raceName.trim().equalsIgnoreCase(meetingName.trim());
    }


    @Transactional
    public void syncCompletedRaceResults(int season) {

        List<Race> completedRaces = raceRepository.findAllByStatusOrderByRaceDateAsc(RaceStatus.COMPLETED);

        for (Race race : completedRaces) {
            List<JolpicaDto.RaceResult> apiResults =
                    jolpicaApiClient.fetchRaceResults(season, race.getRound());

            if (apiResults.isEmpty()) {
                continue;
            }

            for (JolpicaDto.RaceResult apiResult : apiResults) {
                Driver driver = upsertDriver(apiResult.getDriver());
                Team team = upsertTeam(apiResult.getConstructor());

                if (driver.getTeam() == null && team != null) {
                    driver.setTeam(team);
                    driverRepository.save(driver);
                }

                RaceResult result = raceResultRepository
                        .findByRaceIdAndDriverId(race.getId(), driver.getId())
                        .orElseGet(() -> RaceResult.builder()
                                .race(race)
                                .driver(driver)
                                .build());

                result.setTeam(team);
                result.setFinishPosition(parseIntSafe(apiResult.getPosition()));
                result.setGridPosition(parseIntSafe(apiResult.getGrid()));
                result.setPoints(parseIntSafe(apiResult.getPoints()));
                result.setLaps(parseIntSafe(apiResult.getLaps()));
                result.setStatus(apiResult.getStatus());

                raceResultRepository.save(result);
            }

        }
    }

    @Transactional
    public void recalculateDriverStats(int season) {

        List<JolpicaDto.DriverStanding> standings =
                jolpicaApiClient.fetchDriverStandings(season);

        for (JolpicaDto.DriverStanding standing : standings) {
            String driverId = standing.getDriver().getDriverId();

            driverRepository.findByExternalRef(driverId).ifPresent(driver -> {
                driver.setSeasonPoints(parseIntSafe(standing.getPoints()));
                driver.setWins(parseIntSafe(standing.getWins()));

                int podiums = (int) raceResultRepository
                        .findAllByDriverId(driver.getId())
                        .stream()
                        .filter(r -> r.getFinishPosition() != null && r.getFinishPosition() <= 3)
                        .count();
                driver.setPodiums(podiums);

                int racesEntered = raceResultRepository.findAllByDriverId(driver.getId()).size();
                driver.setRacesEntered(racesEntered);

                driverRepository.save(driver);
            });
        }

    }

    private Driver upsertDriver(JolpicaDto.Driver apiDriver) {
        return driverRepository.findByExternalRef(apiDriver.getDriverId())
                .map(existing -> {
                    existing.setFirstName(apiDriver.getGivenName());
                    existing.setLastName(apiDriver.getFamilyName());
                    existing.setCode(apiDriver.getCode());
                    existing.setNationality(apiDriver.getNationality());
                    if (apiDriver.getPermanentNumber() != null) {
                        existing.setNumber(parseIntSafe(apiDriver.getPermanentNumber()));
                    }
                    return driverRepository.save(existing);
                })
                .orElseGet(() -> driverRepository.save(
                        Driver.builder()
                                .externalRef(apiDriver.getDriverId())
                                .firstName(apiDriver.getGivenName())
                                .lastName(apiDriver.getFamilyName())
                                .code(apiDriver.getCode())
                                .nationality(apiDriver.getNationality())
                                .number(parseIntSafe(apiDriver.getPermanentNumber()))
                                .build()
                ));
    }

    private Team upsertTeam(JolpicaDto.Constructor apiConstructor) {
        if (apiConstructor == null) return null;
        return teamRepository.findByExternalRef(apiConstructor.getConstructorId())
                .map(existing -> {
                    existing.setName(apiConstructor.getName());
                    return teamRepository.save(existing);
                })
                .orElseGet(() -> teamRepository.save(
                        Team.builder()
                                .externalRef(apiConstructor.getConstructorId())
                                .name(apiConstructor.getName())
                                .nationality(apiConstructor.getNationality())
                                .build()
                ));
    }

    private Integer parseIntSafe(String value) {
        if (value == null || value.isBlank()) return 0;
        try {
            return (int) Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}