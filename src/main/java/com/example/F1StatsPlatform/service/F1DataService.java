package com.example.F1StatsPlatform.service;

import com.example.F1StatsPlatform.dto.DriverDto;
import com.example.F1StatsPlatform.dto.RaceDto;
import com.example.F1StatsPlatform.dto.RaceResultDto;
import com.example.F1StatsPlatform.entity.Driver;
import com.example.F1StatsPlatform.entity.Race;
import com.example.F1StatsPlatform.entity.RaceResult;
import com.example.F1StatsPlatform.repository.DriverRepository;
import com.example.F1StatsPlatform.repository.RaceRepository;
import com.example.F1StatsPlatform.repository.RaceResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class F1DataService {

    private final RaceRepository raceRepository;
    private final RaceResultRepository raceResultRepository;
    private final DriverRepository driverRepository;

    public List<RaceDto> getSchedule() {
        int season = Year.now().getValue();
        return raceRepository.findAllBySeasonOrderByRoundAsc(season)
                .stream()
                .map(this::toRaceDto)
                .toList();
    }

    public List<RaceResultDto> getRaceResults(Long raceId) {
        return raceResultRepository.findAllByRaceIdOrderByFinishPositionAsc(raceId)
                .stream()
                .map(this::toRaceResultDto)
                .toList();
    }

    public List<DriverDto> getDriverStandings() {
        return driverRepository.findAllByOrderBySeasonPointsDesc()
                .stream()
                .map(this::toDriverDto)
                .toList();
    }

    private RaceDto toRaceDto(Race r) {
        return RaceDto.builder()
                .id(r.getId())
                .round(r.getRound())
                .name(r.getName())
                .circuitName(r.getCircuitName())
                .country(r.getCountry())
                .raceDate(r.getRaceDate())
                .sessionStartUtc(r.getSessionStartUtc())
                .status(r.getStatus())
                .build();
    }

    private RaceResultDto toRaceResultDto(RaceResult rr) {
        Driver d = rr.getDriver();
        return RaceResultDto.builder()
                .finishPosition(rr.getFinishPosition())
                .gridPosition(rr.getGridPosition())
                .driverCode(d != null ? d.getCode() : null)
                .driverFirstName(d != null ? d.getFirstName() : null)
                .driverLastName(d != null ? d.getLastName() : null)
                .teamName(rr.getTeam() != null ? rr.getTeam().getName() : null)
                .points(rr.getPoints())
                .laps(rr.getLaps())
                .status(rr.getStatus())
                .build();
    }

    private DriverDto toDriverDto(Driver d) {
        return DriverDto.builder()
                .id(d.getId())
                .code(d.getCode())
                .number(d.getNumber())
                .firstName(d.getFirstName())
                .lastName(d.getLastName())
                .nationality(d.getNationality())
                .teamName(d.getTeam() != null ? d.getTeam().getName() : null)
                .seasonPoints(d.getSeasonPoints())
                .wins(d.getWins())
                .podiums(d.getPodiums())
                .racesEntered(d.getRacesEntered())
                .build();
    }
}