package ru.wishList.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wishList.models.Friends;
import ru.wishList.models.Users;
import ru.wishList.models.Wishes;
import ru.wishList.repository.FriendRepository;
import ru.wishList.repository.UserRepository;
import ru.wishList.repository.WishRepository;

import javax.servlet.http.HttpSession;

@Controller
public class FriendsController {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WishRepository wishRepository;

    @GetMapping("friends")
    public String showFriends(HttpSession session, Model model) {

        Users user = (Users) session.getAttribute("user");

        Iterable<Friends> friends = friendRepository.findAllByUserId(user.getId());
        model.addAttribute("friends", friends);

        return "friends";
    }

    @GetMapping("add-friend")
    public String newFriend(Model model) {

        model.addAttribute("newFriend", new Friends());

        return "add_friend";
    }

    @PostMapping("add-friend")
    public String addFriend(@ModelAttribute("newFriend") Friends newFriend, HttpSession session) {

        Users userFriend = userRepository.findByName(newFriend.getFriendName());

        if (userFriend != null) {

            Users user = (Users) session.getAttribute("user");

            newFriend.setUserId(user.getId());
            newFriend.setFriendId(userFriend.getId());
            friendRepository.save(newFriend);

            return "redirect:/friends";
        }

        return "wrong_user";
    }

    @GetMapping("{id}")
    public String showFriendsWishes(@PathVariable("id") Long id, Model model) {
        Friends friend = friendRepository.findById(id).get();
        Long userId = friend.getFriendId();
        Iterable<Wishes> wishes = wishRepository.findAllByUserId(userId);

        model.addAttribute("wishes", wishes);
        model.addAttribute("friend", friend);

        return "friend's_wishes";
    }

    @DeleteMapping("/delete-friend")
    public String deleteFriend(@ModelAttribute("friend") Friends friend) {
        friendRepository.delete(friend);

        return "redirect:/friends";
    }
}
