package ru.wishList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.wishList.models.Users;
import ru.wishList.repository.UserRepository;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String RequestData() {
        return "login";
    }

    @PostMapping("/login")
    public String Verify(@RequestParam String name, @RequestParam String password, HttpSession session) {
        Users user = userRepository.findByName(name);

        if (user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/user";
        }

        return "/wrong_password";
    }

}
