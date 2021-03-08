package com.personal.pwnedchecker.event;

import com.personal.pwnedchecker.model.Pwned;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class PwnedEvent extends ApplicationEvent {
    private List<Pwned> pwnedList;
    public PwnedEvent(Object source, List<Pwned> pwnedList) {
        super(source);
        this.pwnedList = pwnedList;
    }
}
