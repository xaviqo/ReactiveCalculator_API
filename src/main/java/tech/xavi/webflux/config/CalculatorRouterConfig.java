package tech.xavi.webflux.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@AllArgsConstructor
@Configuration
public class CalculatorRouterConfig {

    private final RequestHandler handler;

    @Bean
    public RouterFunction<ServerResponse> highCalculatorRouter(){

        return RouterFunctions.route()
                .path("calc", this::calculatorRouter)
                .build();
    }

    private RouterFunction<ServerResponse> calculatorRouter(){

        return RouterFunctions.route()
                .GET("{num1}/{num2}",isOperation("+"),handler::addOperation)
                .GET("{num1}/{num2}",isOperation("-"),handler::subtractOperation)
                .GET("{num1}/{num2}",isOperation("*"),handler::multiplyOperation)
                .GET("{num1}/{num2}",isOperation("/"),handler::divideOperation)
                .GET("{num1}/{num2}", req -> ServerResponse.badRequest().bodyValue("OP should be '+ - * /'. Invalid Request."))
                .build();

    }

    private RequestPredicate isOperation(String operation){
        return RequestPredicates.headers(
                headers -> operation.equals(headers.asHttpHeaders()
                        .toSingleValueMap()
                        .get("op")));
    }

}
