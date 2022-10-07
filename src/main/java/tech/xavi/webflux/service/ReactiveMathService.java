package tech.xavi.webflux.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.xavi.webflux.dto.MultiplyRequestDto;
import tech.xavi.webflux.dto.Response;

import java.time.Duration;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(() -> input*input)
                //.map( value -> new Response(value)) <-- IS THE SAME
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input){
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                //.doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("Reactive math-service processing: "+i))
                .map( value -> new Response(value*input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono){
        return dtoMono
                .map(dto -> dto.getFirst()*dto.getSecond())
                .map(Response::new);
    }
}
