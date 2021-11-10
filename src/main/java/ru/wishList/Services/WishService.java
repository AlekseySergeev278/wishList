package ru.wishList.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.wishList.models.Wishes;
import ru.wishList.repository.WishRepository;


@Service
public class WishService {


    @Autowired
    WishRepository wishRepository;

    /**
     * This method gets user's ID and returns its wishes
     * @param userId - User's id in Long type
     * @return return user's wishes
     */
    @Cacheable(value = "wishes", key = "#userId")
    public Iterable<Wishes> getWishes(Long userId) {
        System.out.println("test2");
        return wishRepository.findAllByUserId(userId);
    }

    /**
     * This method is needed to refresh cache value "wishes"
     * @param userId - User's id in Long type
     * @return return user's wishes
     */
    @CachePut(value = "wishes", key = "#userId")
    public Iterable<Wishes> RefreshGetWishes(Long userId) {
        return wishRepository.findAllByUserId(userId);
    }

    /**
     * This method adds a new user's wish to the database
     * @param newWish - a Wishes object
     * @param userId - a user ID in Long type
     */
    @CachePut(value = "wishes", key="#userId")
    public Iterable<Wishes> addWish(Wishes newWish, Long userId) {
        newWish.setUserId(userId);
        wishRepository.save(newWish);
        return wishRepository.findAllByUserId(userId);
    }

    /**
     * This method gives selected wish
     * @param wishId - wish ID in Long type
     * @return return selected wish as Wishes object
     */
    @Cacheable("wish")
    public Wishes getSpecialWish(Long wishId){
        return wishRepository.findById(wishId).get();
    }

    /**
     * This method updates user's wish
     * @param wish - a Wishes object
     */
    @CachePut(value = "wish", key="#wishId")
    public Wishes updateWish(Wishes wish, Long wishId) {
        wishRepository.save(wish);
        return wish;
    }

    /**
     * This method deletes user's wish
     * @param wishId - id of user's wish in Long type
     */
    @Caching(
            put = {
                    @CachePut(value = "wishes", key = "#userId")
            },
            evict = {
                    @CacheEvict(value = "wish", key="#wishId")
            }
    )
    public Iterable<Wishes> deleteWish(Long wishId, Long userId) {
        wishRepository.deleteById(wishId);
        return wishRepository.findAllByUserId(userId);
    }
}
