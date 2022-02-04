package com.github.juliherms.calculator.config;

import com.github.juliherms.calculator.handler.CalculatorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

/**
 * This class responsible to configure router path
 */
@Configuration
public class RouterConfig {

    @Autowired
    private CalculatorHandler handler;

    @Bean
    public RouterFunction<ServerResponse> highLevelCalculatorRouter() {
        return RouterFunctions.route()
                .path("calculator", this::serverResponseRouterFunction)
                .build();
    }

    /**
     * This method responsible to publish request mappings
     * @return
     */
    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("{a}/{b}", isOperation("+"), handler::additionHandler)
                .GET("{a}/{b}", isOperation("-"), handler::subtractionHandler)
                .GET("{a}/{b}", isOperation("*"), handler::multiplicationHandler)
                .GET("{a}/{b}", isOperation("/"), handler::divisionHandler)
                .GET("{a}/{b}", req -> ServerResponse.badRequest().bodyValue("OP should be: + - * /"))
                .build();

    }

    /**
     * Method responsible to check operation
     * @param operation
     * @return
     */
    private RequestPredicate isOperation(String operation){
        //Verify value of OP header
        return RequestPredicates.headers(
                headers -> operation.equals(headers.asHttpHeaders()
                .toSingleValueMap()
                .get("OP")));
    }

}
