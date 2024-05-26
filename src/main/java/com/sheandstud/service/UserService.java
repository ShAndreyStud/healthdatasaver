package com.sheandstud.service;

import com.sheandstud.model.Doctor;
import com.sheandstud.model.User;
import com.sheandstud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String email, String password, User.Role role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password, passwordEncoder);
        user.setRole(role);
        return userRepository.save(user);
    }


}