package tech.xavi.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.xavi.webflux.dto.Response;

public class Lection05BadRequest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest(){

        Mono<Response> response = webClient
                .get()
                .uri("/reactiveMath/square/{input}/withThrow",5) // 5 is NOT allowed
                .retrieve()
                .bodyToMono(Response.class)
                .doOnError(System.out::println)
                .log();

        StepVerifier.create(response)
                .verifyError(WebClientResponseException.BadRequest.class);

    }
}
