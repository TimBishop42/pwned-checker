package com.personal.pwnedchecker.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PwnedEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishPwnedEvent(final String message) {
        PwnedEvent pwnedEvent = new PwnedEvent(this, message);
        applicationEventPublisher.publishEvent(pwnedEvent);

    }
}
