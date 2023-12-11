package com.revy.domain.entity.user.service;

import com.revy.domain.entity.user.User;

/**
 * Created by Revy on 2023.12.08
 */
public interface UserManager {
    User getOrCreateNewUser(Long userId);
}
