package com.cloudlabs.library.service.common;

import com.cloudlabs.library.model.User;

import java.util.Optional;

public interface FindSaveUserService {
    Optional<User> get(Long id);

    Optional<User> getByUsername(String username);

    User post(User user);
}
