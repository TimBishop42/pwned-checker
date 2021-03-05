package com.personal.pwnedchecker.task;

import com.personal.pwnedchecker.service.PwnedUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientTask implements Runnable{

    @Autowired
    private PwnedUserService pwnedUserService;

    @Autowired
    private HaveIBeenPwnedService haveIBeenPwnedService;

    @Override
    public void run() {

        List<String> userEmails = pwnedUserService.fetchAllUsers();

        //Do the DB check for users, query API
            //DB check needs to cache list of users to minimize DB calls
        //Control passed to HaveIBeenPwnedServoce
        //Do DB check to confirm if this is a new pwned user
        //If Pwned user - fire an event
        //Email provider will consume event and fire email



    }
}
