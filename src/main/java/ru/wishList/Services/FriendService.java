package ru.wishList.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wishList.models.Friends;
import ru.wishList.models.Users;
import ru.wishList.repository.FriendRepository;

@Service
public class FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserService userService;

    //This method gets a Users object and returns all its friends
    public Iterable<Friends> findFriends(Users user) {
        return friendRepository.findAllByUserId(user.getId());
    }

    //This method gets a Friends object and checks if the user exists in the database as an independent user
    public boolean userExist(Friends newFriend) {
        return userService.userExist(newFriend.getFriendName());
    }

    //This method gets a Users object and a Friends object. Then it adds necessary attribute at the Friends object and save it in the database.
    public void addFriend(Users user, Friends newFriend) {
        newFriend.setUserId(user.getId());
        String friendName = newFriend.getFriendName();
        newFriend.setFriendId(userService.getFriendId(friendName));
        friendRepository.save(newFriend);
    }

    //This method gets row's ID and returns a Friends object
    public Friends getFriend(Long id) {
        return friendRepository.findById(id).get();
    }

    public void deleteFriend(Friends friend) {
        friendRepository.delete(friend);
    }
}
