package com.personal.pwnedchecker.event;

import com.personal.pwnedchecker.model.Pwned;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class PwnedEvent extends ApplicationEvent {
    private List<Pwned> pwnedList;
    private String userEmail;
    public PwnedEvent(Object source, List<Pwned> pwnedList, String userEmail) {
        super(source);
        this.pwnedList = pwnedList;
        this.userEmail = userEmail;
    }

    public List<Pwned> getPwnedList() {
        return pwnedList;
    }

    public String getUserEmail() {
        return this.userEmail;
    };
}
