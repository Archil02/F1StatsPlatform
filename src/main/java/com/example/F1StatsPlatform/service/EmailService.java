package com.example.F1StatsPlatform.service;
import com.example.F1StatsPlatform.entity.AppUser;
import com.example.F1StatsPlatform.entity.Race;
import com.example.F1StatsPlatform.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final AppUserRepository appUserRepository;

    @Async
    public void sendRaceStartedEmails(Race race) {
        List<AppUser> users = appUserRepository.findAllByEnabledTrue();
        if (users.isEmpty()) {
            return;
        }

        String subject = "🏁 F1: " + race.getName() + " — რბოლა დაიწყო!";
        String body = buildStartBody(race);

        sendToAll(users, subject, body);
    }

    @Async
    public void sendRaceFinishedEmails(Race race) {
        List<AppUser> users = appUserRepository.findAllByEnabledTrue();
        if (users.isEmpty()) return;

        String subject = "🏆 F1: " + race.getName() + " — რბოლა დასრულდა!";
        String body = buildFinishedBody(race);

        sendToAll(users, subject, body);
    }

    private void sendToAll(List<AppUser> users, String subject, String body) {
        for (AppUser user : users) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject(subject);
                message.setText("გამარჯობა, " + user.getFirstName() + "!\n\n" + body);
                mailSender.send(message);
            } catch (MailException ex) {
                System.out.println("ემაილი ვერ გაიგზავნა: " + user.getEmail() + " — " + ex.getMessage());
            }
        }
    }

    private String buildStartBody(Race race) {
        return String.format(
                """
                %s ახლა იწყება!

                📍 ტრასა:  %s
                🌍 ქვეყანა: %s
                📅 თარიღი:  %s

                გადი ჩვენს ვებ-გვერდზე და ნახე live განახლებები!

                F1 Stats Platform
                """,
                race.getName(),
                race.getCircuitName() != null ? race.getCircuitName() : "—",
                race.getCountry() != null ? race.getCountry() : "—",
                race.getRaceDate()
        );
    }

    private String buildFinishedBody(Race race) {
        return String.format(
                """
                %s დასრულდა!

                📍 ტრასა:  %s
                🌍 ქვეყანა: %s

                შედი ჩვენს ვებ-გვერდზე და ნახე სრული შედეგები და განახლებული სტანდინგები!

                F1 Stats Platform
                """,
                race.getName(),
                race.getCircuitName() != null ? race.getCircuitName() : "—",
                race.getCountry() != null ? race.getCountry() : "—"
        );
    }
}