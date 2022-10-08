package tech.xavi.webflux.config;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.xavi.webflux.dto.MultiplyRequestDto;
import tech.xavi.webflux.dto.Response;
import tech.xavi.webflux.exception.InputValidationException;
import tech.xavi.webflux.service.ReactiveMathService;

import java.util.function.BiFunction;

@AllArgsConstructor
@Service
public class RequestHandler {
    private final ReactiveMathService mathService;


    // CALCULATOR

    public Mono<ServerResponse> addOperation(ServerRequest request) {
        return processOperation(request,(n1,n2) -> ServerResponse.ok().bodyValue(n1+n2));
    }

    public Mono<ServerResponse> subtractOperation(ServerRequest request) {
        return processOperation(request,(n1,n2) -> ServerResponse.ok().bodyValue(n1-n2));

    }
    public Mono<ServerResponse> multiplyOperation(ServerRequest request) {
        return processOperation(request,(n1,n2) -> ServerResponse.ok().bodyValue(n1*n2));
    }

    public Mono<ServerResponse> divideOperation(ServerRequest request) {
        return processOperation(request,(n1,n2) ->
            n2 != 0 ? ServerResponse.ok().bodyValue(n1/n2):ServerResponse.badRequest().bodyValue("Cannot divide by 0")
        );
    }

    private Mono<ServerResponse> processOperation (ServerRequest request,
                                                   BiFunction <Integer,Integer, Mono<ServerResponse>> logic){
        int num1 = getValue(request,"num1");
        int num2 = getValue(request,"num2");
        return logic.apply(num1,num2);
    }

    private int getValue(ServerRequest req, String key){
        return Integer.parseInt(req.pathVariable(key));
    }

    ////////////////

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Response> responseMono = mathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok().body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest){
        Mono<MultiplyRequestDto> requestDtoMono = serverRequest.bodyToMono(MultiplyRequestDto.class);
        Mono<Response> responseMono = mathService.multiply(requestDtoMono);
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if (input < 10 || input > 20)
            return Mono.error(new InputValidationException(input));
        Mono<Response> responseMono = mathService.findSquare(input);
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }

}
