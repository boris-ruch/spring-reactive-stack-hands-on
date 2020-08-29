package com.boo.api.servlet;

import com.boo.domain.User;
import com.boo.repository.BlockingUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/mvccf")
@Slf4j
public class UserEndpointMVCCompletableFuture {

    private final BlockingUserRepository blockingUserRepository;

    @Autowired
    public UserEndpointMVCCompletableFuture(BlockingUserRepository blockingUserRepository) {
        this.blockingUserRepository = blockingUserRepository;
    }

    @GetMapping("users")
    public CompletableFuture<List<User>> getUsers() {
        log.info("call mvc completable future endpoint");
        return CompletableFuture.supplyAsync(() -> StreamSupport
                .stream(blockingUserRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));
    }
}