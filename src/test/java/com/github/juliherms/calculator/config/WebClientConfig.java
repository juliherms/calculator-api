package com.github.juliherms.calculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .filter(this::sessionToken)
                .build();
    }

    /**
     * Method responsible to check auth method. Basic or token
     * @param request
     * @param ex
     * @return
     */
    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
        // check basic or auth
        ClientRequest clientRequest = request.attribute("auth")
                .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                .orElse(request);

        return ex.exchange(clientRequest);
    }

    /**
     * Method responsible to intercept request and add token
     * @param request
     * @return
     */
    private ClientRequest withOAuth(ClientRequest request){
        return ClientRequest.from(request)
                .headers(h -> h.setBearerAuth("some-toke"))
                .build();
    }

    /**
     * Method responsible to intercept request and add basic authentication
     * @param request
     * @return
     */
    private ClientRequest withBasicAuth(ClientRequest request){
        return ClientRequest.from(request)
                .headers(h -> h.setBasicAuth("username","password"))
                .build();
    }
}





















