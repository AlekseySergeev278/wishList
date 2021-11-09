package ru.wishList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.wishList.Services.UserService;
import ru.wishList.models.Users;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String getForm(Model model) {
        model.addAttribute("newUser", new Users());
        return "add_user";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("newUser") Users newUser){
        userService.addUser(newUser);
        return "redirect:/";
    }
}
