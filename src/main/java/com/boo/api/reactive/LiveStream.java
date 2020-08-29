package com.boo.api.reactive;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@RestController
@RequestMapping("/live")
@Slf4j
public class LiveStream {

    @PostConstruct
    public void generateQuotes() {
        val faker = new Faker();
        val facts = Flux
                .fromStream(Stream.generate(() -> faker.chuckNorris().fact()))
                .delayElements(Duration.ofSeconds(1));
        this.collectChuckNorrisFacts(facts);
    }

    @GetMapping(value = "temperature", produces = "text/event-stream")
    public Flux<Integer> getTemperature() {
        val random = new Random();
        val low = 18;
        val high = 32;
        log.info("call temperature endpoint");
        return Flux.fromStream(Stream.generate(() -> random.nextInt(high - low) + low))
                .delayElements(Duration.ofSeconds(1));
    }

    @PostMapping(value = "chucknorris", consumes = "application/stream+json")
    public void collectChuckNorrisFacts(Flux<String> facts) {
        log.info("log Chuck Norris facts");
        facts.doOnNext(log::info).subscribe();
    }
}
