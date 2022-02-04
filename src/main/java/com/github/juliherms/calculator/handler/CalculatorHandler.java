package com.github.juliherms.calculator.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

/**
 * This class responsible to provide operations for calculator
 */
@Service
public class CalculatorHandler {

    /**
     * This method responsible to execute addition operation
     * @param request
     * @return
     */
    public Mono<ServerResponse> additionHandler(ServerRequest request){
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }

    /**
     * This method responsible to execute subtraction operation
     * @param request
     * @return
     */
    public Mono<ServerResponse> subtractionHandler(ServerRequest request){
        return process(request, (a,b) -> ServerResponse.ok().bodyValue(a - b));
    }

    /**
     * This method responsible to execute multiplication operation
     * @param request
     * @return
     */
    public Mono<ServerResponse> multiplicationHandler(ServerRequest request){
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    /**
     * This method responsible to execute division operation.
     * @param request
     * @return
     */
    public Mono<ServerResponse> divisionHandler(ServerRequest request){
        return process(request, (a,b) ->
                b != 0 ? ServerResponse.ok().bodyValue(a / b) :
                         ServerResponse.badRequest().bodyValue("b can not be 0")
                );
    }

    /**
     * This method response to capture value and apply logic
     * @param request
     * @param opLogic
     * @return
     */
    private Mono<ServerResponse> process(ServerRequest request,
                                         BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic){
        int a = getValue(request, "a");
        int b = getValue(request,"b");
        return opLogic.apply(a,b);
    }

    /**
     * Method response to get value by request
     * @param request
     * @param key
     * @return
     */
    private int getValue(ServerRequest request, String key) {
        return Integer.parseInt(request.pathVariable(key));
    }

}
