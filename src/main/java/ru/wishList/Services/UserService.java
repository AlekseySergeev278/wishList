package ru.wishList.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wishList.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // This method gets user's name and checks if the user exists
    public boolean userExist(String name) {
        return userRepository.existsByName(name);
    }

    //This method gets name user's friend and returns its ID
    public Long getFriendId(String name) {
        return userRepository.findByName(name).getId();
    }
}
