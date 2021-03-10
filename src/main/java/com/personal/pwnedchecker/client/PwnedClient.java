package com.personal.pwnedchecker.client;


import com.fasterxml.jackson.core.type.TypeReference;
import com.personal.pwnedchecker.model.Pwned;
import com.personal.pwnedchecker.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class PwnedClient {

    private static final String pwnedApiUrl = "https://haveibeenpwned.com/api/v3/breachedaccount/";

//    private WebClient client = WebClient.create(pwnedHost);

//    private static final String pwnedApiUrl = "api/v3/breachedaccount/";

    private static final String apiKey = "e8fc21303a024e309a68b3c1c59a7536";

    @Autowired
    private WebClient.Builder builder;

//    public Flux<Pwned> getPwnedByUserEmail(String userEmail) {
//        return builder.build()
//                .get()
//                .uri(pwnedApiUrl + userEmail + "?truncateResponse=false")
//                .header("hibp-api-key", apiKey)
//                .retrieve()
//                .bodyToFlux(Pwned.class);
//    }

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
                .bodyToMono(Pwned[].class)
                .log();
    }

    public Mono<List<Response>> getPwnedResponseByUserEmail(String userEmail) {
        return builder.build()
                .get()
                .uri(pwnedApiUrl + userEmail + "?truncateResponse=false")
                .header("hibp-api-key", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Response>>() {
                })
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
