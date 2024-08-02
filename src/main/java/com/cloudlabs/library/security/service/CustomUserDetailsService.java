package com.cloudlabs.library.security.service;

import com.cloudlabs.library.model.User;

import java.util.Optional;

public interface CustomUserDetailsService {

    Optional<User> readByUsername(String username);
}
