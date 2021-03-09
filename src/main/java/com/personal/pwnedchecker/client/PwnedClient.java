package com.personal.pwnedchecker.client;


import com.personal.pwnedchecker.model.Pwned;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Service
public class PwnedClient {

    private static final String pwnedHost = "https://haveibeenpwned.com/";

    private WebClient client = WebClient.create(pwnedHost);

    private static final String pwnedApiUrl = "api/v3/breachedaccount/";

    public Flux<Pwned> getPwnedByUserEmail(String userEmail) {
        return client.get()
                .uri(pwnedApiUrl+userEmail+"?truncateResponse=false")
                .header("Add my API key here")
                .exchangeToFlux(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToFlux(Pwned.class);
                    } else {
                        throw new HttpClientErrorException(clientResponse.statusCode());
                    }
                });
    }

}
