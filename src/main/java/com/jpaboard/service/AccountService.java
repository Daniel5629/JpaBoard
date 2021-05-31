package com.jpaboard.service;

import com.jpaboard.model.Account;
import com.jpaboard.model.Role;
import com.jpaboard.repository.RoleRepository;
import com.jpaboard.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @Transactional
    public Account save(Account user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        Role role = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    @Transactional
    public void login(Account user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(roleRepository.findByName("ROLE_USER").getName())));
        SecurityContextHolder.getContext().setAuthentication(token);

        httpSession.setAttribute("loginNickname", user.getNickname());
    }

    @Transactional(readOnly = true)
    public Account getUser(String username) {
        Account user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return user;
    }

    @Transactional
    public void deleteUser(String username) {
        Account user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        userRepository.deleteById(user.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities(roleRepository.findByName("ROLE_USER").getName())
                .build();

//        return new User(account.getUsername(),
//                        account.getPassword(),
//                        List.of(new SimpleGrantedAuthority(roleRepository.findByName("ROLE_USER").getName())));
    }
}
