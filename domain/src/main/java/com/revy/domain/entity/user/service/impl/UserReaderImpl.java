package com.revy.domain.entity.user.service.impl;

import com.revy.domain.entity.user.User;
import com.revy.domain.entity.user.UserRepository;
import com.revy.domain.entity.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by Revy on 2023.12.08
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

    private final UserRepository repository;


    @Override
    public Optional<User> findById(Long userId) {
        Assert.notNull(userId, "userId must not be null.");
        return repository.findById(userId);
    }
}
