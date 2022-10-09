package tech.xavi.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.xavi.webflux.dto.Response;

public class Lection01SingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest(){

        Response r = webClient
                .get()
                .uri("/math/square/{num}",5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        System.out.println(r);

    }

    @Test
    public void stepVerifierTest(){

        Mono<Response> r = webClient
                .get()
                .uri("/math/square/{num}",5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(r)
                        .expectNextMatches( rs -> rs.getOutput() == 25)
                                .verifyComplete();

        System.out.println(r);

    }
}

