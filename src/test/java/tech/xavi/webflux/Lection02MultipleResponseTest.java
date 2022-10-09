package tech.xavi.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tech.xavi.webflux.dto.Response;

public class Lection02MultipleResponseTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void fluxTestNoStreamingEndpoint(){

        Flux<Response> response = webClient
                .get()
                .uri("/math/table/{num}",5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);
                //.log();

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();

    }

    @Test
    public void fluxTestStreamingEndpoint(){

        Flux<Response> response = webClient
                .get()
                .uri("/reactiveMath/table/{num}/stream",5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println)
                .log();

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();

    }
}
