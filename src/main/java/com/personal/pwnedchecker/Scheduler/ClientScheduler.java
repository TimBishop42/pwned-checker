package com.personal.pwnedchecker.Scheduler;

import com.personal.pwnedchecker.service.PwnedUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientScheduler {

    @Autowired
    private PwnedUserService pwnedUserService;

    @Scheduled(fixedDelay = 600000)
    public void cheduledClientTask() {
        log.info("Checking to see if there are any new account breaches");
        pwnedUserService.checkIfAnyNewBreachesForAllUsers();

    }

}
