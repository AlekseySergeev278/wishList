package ru.wishList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wishList.models.Users;
import ru.wishList.models.Wishes;
import ru.wishList.repository.WishRepository;

import javax.servlet.http.HttpSession;

@Controller
public class WishesController {

    @Autowired
    WishRepository wishRepository;

    @ModelAttribute("user")
    public static Users currentUser(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        return user;
    }

    @ModelAttribute("wishes")
    public Iterable<Wishes> userWishes(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        Long userId = user.getId();

        Iterable<Wishes> wishes = wishRepository.findAllByUserId(userId);

        return wishes;
    }


    @GetMapping("/add-wish")
    public String newWish(Model model) {
        model.addAttribute("wish", new Wishes());

        return "add_wish";
    }

    @PostMapping("/add-wish")
    public String addWish(@ModelAttribute("wish") Wishes wish, HttpSession session) {
        Users user = (Users) session.getAttribute("user");

        wish.setUserId(user.getId());
        wishRepository.save(wish);

        return "redirect:/user";
    }

    @GetMapping("{userId}/{wishName}")
    public String showWish(@PathVariable("userId") Long userId, @PathVariable("wishName") String wishName, Model model) {
        model.addAttribute("wish", wishRepository.findByUserIdAndWishName(userId, wishName));

        return "wish";
    }

    @PatchMapping("/update")
    public String updateWish(@ModelAttribute("wish") Wishes wish, HttpSession session, Model model) {
        wishRepository.save(wish);

        return "redirect:/user";
    }

    @DeleteMapping("/delete")
    public String deleteWish(@ModelAttribute("wish") Wishes wish, HttpSession session, Model model) {
        wishRepository.deleteById(wish.getId());

        return "redirect:/user";
    }


}
