package ru.wishList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wishList.Services.WishService;
import ru.wishList.models.Users;
import ru.wishList.models.Wishes;

import javax.servlet.http.HttpSession;

@Controller
public class WishesController {

    @Autowired
    WishService wishService;

    @ModelAttribute("user")
    public static Users currentUser(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        return user;
    }

    @ModelAttribute("wishes")
    public Iterable<Wishes> userWishes(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        Long userId = user.getId();

        Iterable<Wishes> wishes = wishService.getWishes(userId);

        return wishes;
    }


    @GetMapping("/add-wish")
    public String newWish(Model model) {
        model.addAttribute("wish", new Wishes());

        return "add_wish";
    }

    @PostMapping("/add-wish")
    public String addWish(@ModelAttribute("wish") Wishes newWish, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        wishService.addWish(newWish, user);

        return "redirect:/user";
    }

    @GetMapping("{userId}/{wishName}")
    public String showWish(@PathVariable("userId") Long userId, @PathVariable("wishName") String wishName, Model model) {
        model.addAttribute("wish", wishService.getSpecialWish(userId, wishName));

        return "wish";
    }

    @PatchMapping("/update")
    public String updateWish(@ModelAttribute("wish") Wishes wish) {
        wishService.updateWish(wish);

        return "redirect:/user";
    }

    @DeleteMapping("/delete")
    public String deleteWish(@ModelAttribute("wish") Wishes wish) {
        wishService.deleteWish(wish);

        return "redirect:/user";
    }


}
