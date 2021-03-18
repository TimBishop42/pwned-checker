package com.personal.pwnedchecker.client;


import com.personal.pwnedchecker.model.Pwned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class PwnedClient {

    private static final String pwnedApiUrl = "https://haveibeenpwned.com/api/v3/breachedaccount/";

    //Get an API key from https://haveibeenpwned.com/
    private static final String apiKey = "";

    @Autowired
    private WebClient.Builder builder;

    public Mono<List<Pwned>> getPwnedByUserEmailMono(String userEmail) {
        return builder.build()
                .get()
                .uri(pwnedApiUrl + userEmail + "?truncateResponse=false")
                .header("hibp-api-key", apiKey)
                .exchangeToFlux(clientResponse ->
                        clientResponse.bodyToFlux(Pwned.class))
                .collectList();
    }

    public Mono<Pwned[]> getPwnedListByUserEmail(String userEmail) {
        return builder.build()
                .get()
                .uri(pwnedApiUrl + userEmail + "?truncateResponse=false")
                .header("hibp-api-key", apiKey)
                .retrieve()
                .bodyToMono(Pwned[].class);
    }

    public Mono<Object[]> getPwnedResponseByUserEmail(String userEmail) {
        return builder.build()
                .get()
                .uri(pwnedApiUrl + userEmail + "?truncateResponse=false")
                .header("hibp-api-key", apiKey)
                .retrieve()
                .bodyToMono(Object[].class)
                .log();
    }

    public Flux<String> getPwnedStringByUserEmail(String userEmail) {
        return builder.build()
                .get()
                .uri(pwnedApiUrl + userEmail + "?truncateResponse=false")
                .header("hibp-api-key", apiKey)
                .exchangeToFlux(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToFlux(String.class);
                    } else {
                        throw new HttpClientErrorException(clientResponse.statusCode());
                    }
                });
    }

}
