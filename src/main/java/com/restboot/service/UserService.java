package com.restboot.service;

import com.restboot.model.Role;
import com.restboot.model.User;
import com.restboot.repository.RoleRepository;
import com.restboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        String encodedPwd = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPwd);
        user.setEnabled(true);

        Role role = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return user;
    }
}
