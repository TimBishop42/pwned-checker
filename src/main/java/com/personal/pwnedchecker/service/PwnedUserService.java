package com.personal.pwnedchecker.service;


import com.personal.pwnedchecker.client.PwnedClient;
import com.personal.pwnedchecker.event.PwnedEventPublisher;
import com.personal.pwnedchecker.model.Pwned;
import com.personal.pwnedchecker.model.PwnedUser;
import com.personal.pwnedchecker.repository.PwnedRepository;
import com.personal.pwnedchecker.repository.PwnedUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PwnedUserService {

    @Autowired
    private PwnedUserRepository pwnedUserRepository;

    @Autowired
    private PwnedClient pwnedClient;

    @Autowired
    private PwnedRepository pwnedRepository;

    @Autowired
    private PwnedEventPublisher pwnedEventPublisher;


    @Cacheable("userEmails")
    private List<String> fetchAllUsers() {
        return pwnedUserRepository.findAll().stream()
                .map(PwnedUser::getUserEmail)
                .collect(Collectors.toList());
    }

    private List<Pwned> retrievePwnedListForUser(String userEmail) {
        Flux<Pwned> result = pwnedClient.getPwnedByUserEmail("Test");
        return result.toStream().collect(Collectors.toList());
    }

    private List<Pwned> checkIfNewPwning(List<Pwned> pwnList, List<Pwned> alreadyPwned) {
        return pwnList.stream()
                .filter(pwned -> alreadyPwned.stream().map(Pwned::getPwnCount).noneMatch(pwnedCount -> pwnedCount.equals(pwned.getPwnCount())))
                .collect(Collectors.toList());
    }

    public void checkIfAnyNewBreachesForAllUsers() {
        List<String> userEmails = fetchAllUsers();
        userEmails.stream().forEach(userEmail -> {
            List<Pwned> alreadyPwned = pwnedRepository.findPwnedByUserEmail(userEmail);
            List<Pwned> newBreach = checkIfNewPwning(retrievePwnedListForUser(userEmail), alreadyPwned);
            if (newBreach != null && newBreach.size() > 0) {
                log.info("New breach detected for user {}, number of breaches {}", userEmail, newBreach.size());
                updateEmailAndSaveNewBreaches(newBreach, userEmail);
                pwnedEventPublisher.publishPwnedEvent(newBreach);
            }
            else {
                log.info("No new account breaches found for user email: {}", userEmail);
            }
        });

    }

    private void updateEmailAndSaveNewBreaches(List<Pwned> newBreach, String userEmail) {
        newBreach.forEach(pwned -> pwned.setUserEmail(userEmail));
        log.info("Saving new breaches");
        pwnedRepository.saveAll(newBreach);

    }
}
