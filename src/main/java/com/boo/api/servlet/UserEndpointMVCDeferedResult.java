package com.boo.api.servlet;

import com.boo.domain.User;
import com.boo.repository.BlockingUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/mvcdeferredresult")
@Slf4j
public class UserEndpointMVCDeferedResult {

    private final BlockingUserRepository blockingUserRepository;

    @Autowired
    public UserEndpointMVCDeferedResult(BlockingUserRepository blockingUserRepository) {
        this.blockingUserRepository = blockingUserRepository;
    }

    @GetMapping("users")
    public DeferredResult<ResponseEntity<List<User>>> getUsers() {
        log.info("call mvc deferred result endpoint");
        DeferredResult<ResponseEntity<List<User>>> output = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            output.setResult(ResponseEntity.ok(StreamSupport
                    .stream(blockingUserRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList())));
        });
        return output;
    }
}