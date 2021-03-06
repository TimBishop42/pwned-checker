package com.personal.pwnedchecker.service;


import com.personal.pwnedchecker.event.PwnedEvent;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendBreachNotification(PwnedEvent pwnedEvent) {

        StringBuilder sb = new StringBuilder();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(pwnedEvent.getUserEmail());
        msg.setCc("bishoptim453@gmail.com");

        msg.setSubject("New Account Breach Notification");

        sb.append("Hi \nthe email account breach utility has detected a new breach against your email address, ask Tim if you're not sure what to do about this: \n");
        pwnedEvent.getPwnedList().forEach(pwned -> {
            sb.append("\n\nCompany/Website: \n")
                    .append(pwned.getDomain())
                    .append("\n\nBreach Date: \n")
                    .append(pwned.getBreachDate())
                    .append("\n\nCompromised Data: \n")
                    .append(pwned.getDataClasses())
                    .append("\n\nDescription: \n")
                    .append(Jsoup.parse(pwned.getRawDescription()).text());
        });

        msg.setText(sb.toString());
        javaMailSender.send(msg);
        log.info("Breach notification mail sent to user {}", pwnedEvent.getUserEmail());
    }
}
