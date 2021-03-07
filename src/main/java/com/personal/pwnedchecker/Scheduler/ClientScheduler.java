package com.personal.pwnedchecker.Scheduler;

import com.personal.pwnedchecker.model.Pwned;
import com.personal.pwnedchecker.service.PwnedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientScheduler {

    @Autowired
    private PwnedUserService pwnedUserService;

    @Scheduled(fixedDelay = 10000)
    public void cheduledClientTask() {
        List<String> userEmails = pwnedUserService.fetchAllUsers();

        List<Pwned> pwneByUserEmail = pwnedUserService.retrievePwnedListForUser("Test");

        System.out.println("Pwned!: "+ pwneByUserEmail.get(1));


    }

}
