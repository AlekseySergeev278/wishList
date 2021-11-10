package ru.wishList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wishList.Services.FriendService;
import ru.wishList.Services.WishService;
import ru.wishList.models.Friends;
import ru.wishList.models.Users;
import ru.wishList.models.Wishes;

import javax.servlet.http.HttpSession;

@Controller
public class FriendsController {

    @Autowired
    FriendService friendService;

    @Autowired
    WishService wishService;

    //This method catches the path to "friends" and shows the user's friends
    @GetMapping("friends")
    public String showFriends(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        model.addAttribute("friends", friendService.findFriends(userId));

        return "friends";
    }

    //This method catches the path "add-friend" and gives a template with form for adding a new friend
    @GetMapping("add-friend")
    public String newFriend(Model model) {

        model.addAttribute("newFriend", new Friends());

        return "add_friend";
    }

    //This method gets data from form in order to add a new friend and save new Friends object in the database
    @PostMapping("add-friend")
    public String addFriend(@ModelAttribute("newFriend") Friends newFriend, HttpSession session) {

        if (friendService.userExist(newFriend)) {

            Long userId = (Long) session.getAttribute("userId");
            friendService.addFriend(userId, newFriend);

            return "redirect:/friends";
        }

        return "wrong_user";
    }

    //This method shows friend's wishes
    @GetMapping("{friendId}")
    public String showFriendsWishes(@PathVariable("friendId") Long friendId, Model model) {
        Friends friend = friendService.getFriend(friendId);
        Long userId = friend.getFriendId();
        Iterable<Wishes> wishes = wishService.getWishes(userId);

        model.addAttribute("wishes", wishes);
        model.addAttribute("friend", friend);

        return "friend's_wishes";
    }

    //This method delete friend
    @DeleteMapping("/delete-friend")
    public String deleteFriend(@ModelAttribute("friend") Friends friend) {
        Long friendId = friend.getFriendId();
        Long userId = friend.getUserId();

        friendService.deleteFriend(friend, friendId, userId);

        return "redirect:/friends";
    }
}
