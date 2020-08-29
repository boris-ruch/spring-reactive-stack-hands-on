package com.boo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {

    private static final WebClient WEB_CLIENT = WebClient.builder().build();

    public static final int NUMBER_OF_REQUESTS = 7000;

    enum Stack {
        MVC, WEBFLUX
    }

    public static void main(String[] args) throws InterruptedException {

        //run(Stack.MVC);
        run(Stack.WEBFLUX);
    }

    private static void run(Stack stack) throws InterruptedException {
        val atomicInteger = new AtomicInteger();
        val start = Instant.now().toEpochMilli();
        Flux.range(0, NUMBER_OF_REQUESTS).publishOn(Schedulers.elastic())
                .subscribe(i -> WEB_CLIENT.get().uri("http://localhost:8080/" + stack.name().toLowerCase() + "/users")
                        .retrieve()
                        .bodyToMono(String.class)
                        .subscribe(res -> {
                            log.info("counter: {}", atomicInteger.incrementAndGet());
                            if (atomicInteger.get() == NUMBER_OF_REQUESTS) {
                                log.info("*** Took: {} millis ", (Instant.now().toEpochMilli() - start));
                            }
                        }));

        Thread.sleep(25000);

    }

}
