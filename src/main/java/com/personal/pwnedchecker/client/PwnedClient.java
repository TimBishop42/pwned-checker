package com.personal.pwnedchecker.client;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class PwnedClient {
    private WebClient client = WebClient.create("http://localhost:8080");

    private Mono<ClientResponse> result = client.get()
            .uri("/hello")
            .accept(MediaType.TEXT_PLAIN)
            .header("Add my API key here")
            .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));
}
