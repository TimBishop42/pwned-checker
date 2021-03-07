package com.personal.pwnedchecker.service;


import com.personal.pwnedchecker.client.PwnedClient;
import com.personal.pwnedchecker.model.Pwned;
import com.personal.pwnedchecker.model.PwnedUser;
import com.personal.pwnedchecker.repository.PwnedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PwnedUserService {

    @Autowired
    private PwnedUserRepository pwnedUserRepository;

    @Autowired
    private PwnedClient pwnedClient;


    @Cacheable("userEmails")
    public List<String> fetchAllUsers() {
        return pwnedUserRepository.findAll().stream()
        .map(PwnedUser::getUserEmail)
        .collect(Collectors.toList());
    }

    public List <Pwned>retrievePwnedListForUser (String userEmail) {
        Flux<Pwned> result = pwnedClient.getPwnedByUserEmail("TEst");
        return result.toStream().collect(Collectors.toList());
    }
}
