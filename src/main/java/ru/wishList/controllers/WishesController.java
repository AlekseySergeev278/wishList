package ru.wishList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wishList.Services.WishService;
import ru.wishList.models.Wishes;

import javax.servlet.http.HttpSession;

@Controller
public class WishesController {

    @Autowired
    WishService wishService;

    //It's to add a wish
   @GetMapping("/add-wish")
    public String newWish(Model model) {
        model.addAttribute("wish", new Wishes());

        return "add_wish";
    }

    //It's to add a wish
    @PostMapping("/add-wish")
    public String addWish(@ModelAttribute("wish") Wishes newWish, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        wishService.addWish(newWish, userId);

        return "redirect:/user";
    }

    //It shows wish page
    @GetMapping("/wish/{wishId}")
    public String showWish(@PathVariable("wishId") Long wishId, Model model) {
        model.addAttribute("wish", wishService.getSpecialWish(wishId));

        return "wish";
    }

    @PatchMapping("/update/{userId}/{wishId}")
    public String updateWish(@PathVariable("userId") Long userId, @PathVariable("wishId") Long wishId, @ModelAttribute("wish") Wishes wish) {
        wishService.updateWish(wish, wishId);
        wishService.RefreshGetWishes(userId);
        return "redirect:/user";
    }

    //It's to delete a wish
    @DeleteMapping("/delete/{wishId}")
    public String deleteWish(@PathVariable("wishId") Long wishId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        wishService.deleteWish(wishId, userId);

        return "redirect:/user";
    }


}
