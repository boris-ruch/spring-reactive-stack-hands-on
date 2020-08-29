package com.boo.api.servlet;

import com.boo.domain.User;
import com.boo.repository.BlockingUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/mvc")
@Slf4j
public class UserEndpointMVC {

    private final BlockingUserRepository blockingUserRepository;

    @Autowired
    public UserEndpointMVC(BlockingUserRepository blockingUserRepository) {
        this.blockingUserRepository = blockingUserRepository;
    }

    @GetMapping("users")
    public List<User> getUsers() {
        log.info("call mvc endpoint");
        return StreamSupport
                .stream(blockingUserRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}