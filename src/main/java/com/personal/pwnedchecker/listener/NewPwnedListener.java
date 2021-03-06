package com.personal.pwnedchecker.listener;

import com.personal.pwnedchecker.event.PwnedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NewPwnedListener implements ApplicationListener <PwnedEvent> {

    @Override
    public void onApplicationEvent(PwnedEvent applicationEvent) {
        //Send a mail to the impacted user
        //To start, only send mails to my email
    }
}
