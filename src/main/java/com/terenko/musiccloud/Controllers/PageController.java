package com.terenko.musiccloud.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/")
    public String main() {
        return "index";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }
}
