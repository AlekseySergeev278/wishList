package ru.wishList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wishList.Services.UserService;
import ru.wishList.Services.WishService;
import ru.wishList.models.Users;

import javax.servlet.http.HttpSession;


@Controller
public class UsersController {

    @Autowired
    WishService wishService;

    @Autowired
    UserService userService;

    // Catch path "user" and shows its page
    @GetMapping("/user")
    public String getUser(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        model.addAttribute("user", userService.getUser(userId));
        model.addAttribute("wishes", wishService.getWishes(userId));

        return "user_page";
    }

}
