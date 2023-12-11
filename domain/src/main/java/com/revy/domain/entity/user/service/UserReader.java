package com.revy.domain.entity.user.service;

import com.revy.domain.entity.user.User;

import java.util.Optional;

/**
 * Created by Revy on 2023.12.08
 */
public interface UserReader {
    Optional<User> findById(Long userId);
}
