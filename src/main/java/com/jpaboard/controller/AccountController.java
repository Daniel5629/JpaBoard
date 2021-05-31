package com.jpaboard.controller;

import com.jpaboard.model.Account;
import com.jpaboard.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService userService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String register(Account user) {
        userService.save(user);
        return "redirect:/account/login";
    }

    @DeleteMapping("/secession")
    public String secession(Authentication authentication) {
        userService.deleteUser(authentication.getName());
        return "redirect:/";
    }

    @GetMapping("/my-info")
    public String myInfo(Model model, @RequestParam String username) {
        Account user = userService.getUser(username);

        model.addAttribute("user", user);

        return "board/myPage";
    }
}
