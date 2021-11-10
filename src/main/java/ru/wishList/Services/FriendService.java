package ru.wishList.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.wishList.models.Friends;
import ru.wishList.repository.FriendRepository;

@Service
public class FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserService userService;

    /**
     * This method gets a Users object and returns all its friends
     * @param userId - a user's ID in Long type
     * @return return Iterable object with user's friends
     */
    @Cacheable(value="friends", key="#userId")
    public Iterable<Friends> findFriends(Long userId) {
        return friendRepository.findAllByUserId(userId);
    }

    /**
     * This method gets a Friends object and checks if the user exists in the database as an independent user
     * @param newFriend - a Friends object
     * @return return boolean value
     */
    public boolean userExist(Friends newFriend) {
        return userService.userExist(newFriend.getFriendName());
    }

    /**
     * This method gets a Users object and a Friends object. Then it adds necessary attribute at the Friends object and save it in the database.
     * @param userId - a user's ID in Long type
     * @param newFriend - a Friends object
     */
    @CachePut(value="friends", key="#userId")
    public Iterable<Friends> addFriend(Long userId, Friends newFriend) {
        String friendName = newFriend.getFriendName();
        Long friendId = userService.getFriendId(friendName);

        newFriend.setFriendId(friendId);
        newFriend.setUserId(userId);

        friendRepository.save(newFriend);

        return friendRepository.findAllByUserId(userId);
    }

    /**
     * This method gets row's ID and returns a Friends object
     * @param friendId - friend's user ID in Long type
     * @return return Friends object
     */
    @Cacheable(value = "friend", key="#friendId")
    public Friends getFriend(Long friendId) {
        return friendRepository.findById(friendId).get();
    }


    /**
     * This method delete a friend
     * @param friend - A Friends object
     * @param friendId - Friend's ID in Long type
     * @param userId- User's ID in Long type
     * @return return iterable object with user's friends
     */
    @Caching(
            put = {
                @CachePut(value="friends", key="#userId")
            },
            evict = {
                    @CacheEvict(value = "friend", key="#friendId")
            }
    )
    public Iterable<Friends> deleteFriend(Friends friend, Long friendId, Long userId) {
        friendRepository.delete(friend);
        return friendRepository.findAllByUserId(userId);
    }
}
