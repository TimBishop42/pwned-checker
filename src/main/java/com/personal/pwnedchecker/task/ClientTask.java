package com.personal.pwnedchecker.task;

import com.personal.pwnedchecker.model.Pwned;
import com.personal.pwnedchecker.service.PwnedUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientTask implements Runnable{

    @Autowired
    private PwnedUserService pwnedUserService;

    @Override
    public void run() {

//        List<String> userEmails = pwnedUserService.fetchAllUsers();
//
//        List<Pwned> pwneByUserEmail = pwnedUserService.retrievePwnedListForUser("Test");

//        System.out.println("Pwned!: "+ pwneByUserEmail.get(1));

        //Do the DB check for users, query API
            //DB check needs to cache list of users to minimize DB calls
        //Control passed to HaveIBeenPwnedServoce
        //Do DB check to confirm if this is a new pwned user
        //If Pwned user - fire an event
        //Email provider will consume event and fire email



    }
}
