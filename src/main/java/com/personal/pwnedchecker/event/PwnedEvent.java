package com.personal.pwnedchecker.event;

import org.springframework.context.ApplicationEvent;

public class PwnedEvent extends ApplicationEvent {
    private String message;
    public PwnedEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
