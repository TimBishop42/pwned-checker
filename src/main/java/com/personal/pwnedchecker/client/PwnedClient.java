package com.personal.pwnedchecker.client;


import com.personal.pwnedchecker.model.Pwned;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Service
public class PwnedClient {

    private WebClient client = WebClient.create("http://localhost:9090/");

    public Flux<Pwned> getPwnedByUserEmail(String userEmail) {
        return client.get()
                .uri("/testPwned")
                .header("Add my API key here")
                .retrieve()
                .bodyToFlux(Pwned.class);
    }
}
