package com.cloudlabs.library.service.common.impl;

import com.cloudlabs.library.model.User;
import com.cloudlabs.library.repository.UserRepository;
import com.cloudlabs.library.service.common.FindSaveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindSaveUserServiceImpl implements FindSaveUserService {

    private final UserRepository userRepository;

    @Autowired
    public FindSaveUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User post(User user) {
        return userRepository.save(user);
    }
}
