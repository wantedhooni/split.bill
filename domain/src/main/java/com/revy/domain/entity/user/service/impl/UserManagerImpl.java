package com.revy.domain.entity.user.service.impl;

import com.revy.domain.entity.user.User;
import com.revy.domain.entity.user.UserRepository;
import com.revy.domain.entity.user.service.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by Revy on 2023.12.08
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {

    private final UserRepository repository;

    @Override
    public User getOrCreateNewUser(Long userId) {
        return repository.findById(userId).orElse(
                repository.save(
                        User.builder()
                                .id(userId)
                                .name("Test_" + userId)
                                .email("Test_" + userId + "@gmail.com")
                                .balance(new BigDecimal(10000000)) // 테스트를 위하여 초기 생성시 잔고를 추가해놓는다.
                                .build())
        );
    }
}
