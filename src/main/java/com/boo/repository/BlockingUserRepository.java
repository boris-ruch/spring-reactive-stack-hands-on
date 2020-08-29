package com.boo.repository;

import com.boo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class BlockingUserRepository implements BlockingRepository<User> {

    private final ReactiveRepository<User> reactiveRepository;

    private int callCount = 0;

    public BlockingUserRepository() {
        reactiveRepository = new ReactiveUserRepository();
    }

    public BlockingUserRepository(long delayInMs) {
        reactiveRepository = new ReactiveUserRepository(delayInMs);
    }

    public BlockingUserRepository(User... users) {
        reactiveRepository = new ReactiveUserRepository(users);
    }

    public BlockingUserRepository(long delayInMs, User... users) {
        reactiveRepository = new ReactiveUserRepository(delayInMs, users);
    }


    @Override
    public void save(User user) {
        callCount++;
        reactiveRepository.save(Mono.just(user)).block();
    }

    @Override
    public User findFirst() {
        callCount++;
        return reactiveRepository.findFirst().block();
    }

    @Override
    public Iterable<User> findAll() {
        callCount++;
//        try {
//            TimeUnit.MILLISECONDS.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return reactiveRepository.findAll().toIterable();
    }

    @Override
    public User findById(String username) {
        callCount++;
        return reactiveRepository.findById(username).block();
    }

    public int getCallCount() {
        return callCount;
    }
}
