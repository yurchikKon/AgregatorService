package com.example.agregator.services;

import com.example.agregator.entities.Role;
import com.example.agregator.entities.User;
import com.example.agregator.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean createUser(String login, String password){
        if(userRepository.findByLogin(login) != null) return false;

        User user = new User(login, passwordEncoder.encode(password));
        user.setActive(true);
        user.getRoles().add(Role.USER);
        log.info("Saving new User {}", login);
        userRepository.save(user);
        log.info("Saved user.");
        return true;
    }

}
