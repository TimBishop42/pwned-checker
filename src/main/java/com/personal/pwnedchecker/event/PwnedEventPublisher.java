package com.personal.pwnedchecker.event;

import com.personal.pwnedchecker.model.Pwned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PwnedEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishPwnedEvent(final List<Pwned> pwnedList) {
        PwnedEvent pwnedEvent = new PwnedEvent(this, pwnedList);
        applicationEventPublisher.publishEvent(pwnedEvent);

    }
}
