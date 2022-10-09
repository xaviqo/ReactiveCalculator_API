package tech.xavi.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.xavi.webflux.dto.MultiplyRequestDto;
import tech.xavi.webflux.dto.Response;

public class Lection04HeadersTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest(){

        Mono<Response> responseMono =  webClient
                        .post()
                        .uri("reactiveMath/multiply")
                        .bodyValue(buildRequestDto(5,2))
                        .headers(h -> h.set("someKey","someValue"))
                        .retrieve()
                        .bodyToMono(Response.class)
                        .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();

    }

    private MultiplyRequestDto buildRequestDto(int a, int b){
        return new MultiplyRequestDto(a,b);
    }
}
