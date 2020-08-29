package com.boo.api.reactive;

import com.boo.domain.User;
import com.boo.repository.BlockingUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/webflux")
@Slf4j
public class UserEndpointWebflux {

    private final BlockingUserRepository blockingUserRepository;

    @Autowired
    public UserEndpointWebflux(BlockingUserRepository blockingUserRepository) {
        this.blockingUserRepository = blockingUserRepository;
    }

    @GetMapping("users")
    public Flux<User> getUsers() {
        log.info("call webflux endpoint");
        return Flux.defer(() -> Flux.fromIterable(blockingUserRepository.findAll()))
                .subscribeOn(Schedulers.elastic());
    }
}
