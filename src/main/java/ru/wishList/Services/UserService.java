package ru.wishList.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.wishList.models.Users;
import ru.wishList.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /** This method gets user's name and checks if the user exists
     * @param name - User's name
     * @return return true if user exists and false if not
     */
    public boolean userExist(String name) {
        return userRepository.existsByName(name);
    }

    /**This method gets name user's friend and returns its ID
     * @param name - User's name
     * @return return user's ID in Long type
     */
    @Cacheable("friend's id")
    public Long getFriendId(String name) {
        return userRepository.findByName(name).getId();
    }

    /**
     * This method gives User by its name
     * @param name - User's name
     * @return return a Users object
     */
    @Cacheable("user")
    public Users getUser(String name) {
        return userRepository.findByName(name);
    }

    /**
     * This method gives User by its ID
     * @param userId - User's ID in Long type
     * @return return a Users object
     */
    @Cacheable("user")
    public Users getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    /**
     * This method adds a new user to the database
     * @param newUser - a User object
     */
    public void addUser(Users newUser) {
        userRepository.save(newUser);
    }
}
