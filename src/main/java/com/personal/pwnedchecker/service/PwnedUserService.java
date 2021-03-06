package com.personal.pwnedchecker.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.pwnedchecker.client.PwnedClient;
import com.personal.pwnedchecker.event.PwnedEventPublisher;
import com.personal.pwnedchecker.model.Pwned;
import com.personal.pwnedchecker.model.PwnedUser;
import com.personal.pwnedchecker.repository.PwnedRepository;
import com.personal.pwnedchecker.repository.PwnedUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
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

//    private List<Pwned> retrievePwnedListForUser(String userEmail) {
//        Flux<Pwned> result = pwnedClient.getPwnedByUserEmail(userEmail);
//        return result.toStream().collect(Collectors.toList());
//    }

    private List<String> retrievePwnedStringListForUser(String userEmail) {
        Flux<String> result = pwnedClient.getPwnedStringByUserEmail(userEmail);
        return result.toStream().collect(Collectors.toList());
    }

    private List<Pwned> checkIfNewPwning(List<Pwned> pwnList, List<Pwned> alreadyPwned) {
        return pwnList.stream()
                .filter(pwned -> alreadyPwned.stream().map(Pwned::getPwnCount).noneMatch(pwnedCount -> pwnedCount.equals(pwned.getPwnCount())))
                .collect(Collectors.toList());
    }

    private Pwned[] unmarshallClientResponse(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.configure(DeserializationFeature., false);

        try {
            return objectMapper.readValue(jsonResponse, Pwned[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void checkIfAnyNewBreachesForAllUsers() {
        List<String> userEmails = fetchAllUsers();
        userEmails.stream().forEach(userEmail -> {
            List<Pwned> alreadyPwned = pwnedRepository.findPwnedByUserEmail(userEmail);
            List<Pwned> responseList = Arrays.asList(pwnedClient.getPwnedListByUserEmail(userEmail).block());
            List<Pwned> newBreach = checkIfNewPwning(responseList, alreadyPwned);
            try {
                if (newBreach != null && newBreach.size() > 0) {
                    log.info("New breach detected for user {}, number of breaches {}", userEmail, newBreach.size());
                    updateEmailAndSaveNewBreaches(newBreach, userEmail);
                    pwnedEventPublisher.publishPwnedEvent(newBreach, userEmail);
                } else {
                    log.info("No new account breaches found for user email: {}", userEmail);
                }
            }
            catch (DataException e) {
                log.info("Problem saving new breach - user email not generated. Error:",e.getMessage() );
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("Unable to sleep execution thread - Pwned API will reject our next call. Error {}", e.toString());
                e.printStackTrace();
            }
        });

    }

    private List<Pwned> transformObjectArrayToPwnedList(Object[] responses) {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return Arrays.stream(responses).map(response -> mapper.convertValue(response, Pwned.class)).collect(Collectors.toList());
    }

    private void updateEmailAndSaveNewBreaches(List<Pwned> newBreach, String userEmail){
        newBreach.forEach(pwned -> {
            pwned.setUserEmail(userEmail);
            pwned.truncDescription();
        });
        log.info("Saving new breaches");
        pwnedRepository.saveAll(newBreach);
    }
}
