package tech.xavi.webflux.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.xavi.webflux.dto.Response;
import tech.xavi.webflux.exception.InputValidationException;
import tech.xavi.webflux.service.ReactiveMathService;

@AllArgsConstructor
@RestController
@RequestMapping("/reactiveMath")
public class ReactiveMathValidationController {

    private final ReactiveMathService mathService;

    @GetMapping("/square/{input}/withThrow")
    public Mono<Response> findSquare(@PathVariable int input){
        if (input < 10 || input > 20) throw new InputValidationException(input);
        return mathService.findSquare(input);
    }

    @GetMapping("/square/{input}/monoError")
    public Mono<Response> monoError(@PathVariable int input){
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if (integer >= 10 && integer <= 20)
                        sink.next(integer);
                    else
                        sink.error(new InputValidationException(integer));
                })
                .cast(Integer.class)
                .flatMap(i -> this.mathService.findSquare(i));
    }

    @GetMapping("/square/{input}/simpleAssignment")
    public Mono<ResponseEntity<Response>> simpleAssignment(@PathVariable int input){
        return Mono.just(input)
                .filter( i -> i >= 10 && i <= 20)
                .flatMap(this.mathService::findSquare)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
