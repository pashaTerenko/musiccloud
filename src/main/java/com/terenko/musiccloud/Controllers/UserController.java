package com.terenko.musiccloud.Controllers;

import com.terenko.musiccloud.Service.UserService;
import com.terenko.musiccloud.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,

                         Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register";
        }

        PasswordEncoder encoder= new StandardPasswordEncoder();
        String passHash = encoder.encode(password);

        CustomUser dbUser = new CustomUser(login, passHash);
        userService.addUser(dbUser);

        return "redirect:/";
    }
    public String unauthorized(Model model){
        return "unauthorized";
    }
}
