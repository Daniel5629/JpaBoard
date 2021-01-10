package com.restboot.service;

import com.restboot.model.Role;
import com.restboot.model.User;
import com.restboot.repository.RoleRepository;
import com.restboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encodedPwd = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPwd);
        user.setEnabled(true);

        Role role = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(role);

        return userRepository.save(user);
    }
}
