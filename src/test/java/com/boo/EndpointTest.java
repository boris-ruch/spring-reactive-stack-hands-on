package com.boo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Slf4j
public class EndpointTest {

    private final WebClient webClient = WebClient.builder().build();

    @Test
    void runReactive() {
        val atomicInteger = new AtomicInteger();

        Flux.range(0, 5000).publishOn(Schedulers.parallel())
                .subscribe(s -> webClient.get().uri("http://localhost:8080/webflux/users")
                        .retrieve()
                        .bodyToMono(String.class)
                        .subscribe(b -> log.info("result: {}, counter: {}", s, atomicInteger.incrementAndGet())));
    }
}
