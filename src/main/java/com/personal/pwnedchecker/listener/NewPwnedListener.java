package com.personal.pwnedchecker.listener;

import com.personal.pwnedchecker.event.PwnedEvent;
import com.personal.pwnedchecker.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewPwnedListener implements ApplicationListener <PwnedEvent> {

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(PwnedEvent applicationEvent) {
        //Send a mail to the impacted user by calling emailService
        //To start, only send mails to my email
    }
}
