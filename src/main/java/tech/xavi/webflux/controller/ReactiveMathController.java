package tech.xavi.webflux.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.xavi.webflux.dto.MultiplyRequestDto;
import tech.xavi.webflux.dto.Response;
import tech.xavi.webflux.service.ReactiveMathService;

@AllArgsConstructor
@RestController
@RequestMapping("/reactiveMath")
public class ReactiveMathController {

    private final ReactiveMathService mathService;

    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input){
        return mathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input){
        return mathService.multiplicationTable(input);
    }

    @GetMapping(value = "/table/{input}/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input){
        return mathService.multiplicationTable(input);
    }

    @PostMapping("/multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono){
        return mathService.multiply(requestDtoMono);
    }

}
