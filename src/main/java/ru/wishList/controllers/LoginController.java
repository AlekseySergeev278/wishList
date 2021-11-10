package ru.wishList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.wishList.Services.UserService;
import ru.wishList.models.Users;


import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String RequestData() {
        return "login";
    }

    @PostMapping("/login")
    public String Verify(@RequestParam String name, @RequestParam String password, HttpSession session) {
        Users user = userService.getUser(name);

        if (user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());

            return "redirect:/user";
        }

        return "/wrong_password";
    }

}
