package com.restboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/board/list")
    public String list() {
        return "board/list";
    }
}
