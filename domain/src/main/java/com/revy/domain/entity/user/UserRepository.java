package com.revy.domain.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Revy on 2023.12.08
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
