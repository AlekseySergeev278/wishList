package ru.wishList.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wishList.models.Users;
import ru.wishList.models.Wishes;
import ru.wishList.repository.WishRepository;

@Service
public class WishService {

    @Autowired
    WishRepository wishRepository;

    /**This method gets user's ID and returns its wishes
     * @param id - User's id in Long type
     * @return return user's wishes
     */
    public Iterable<Wishes> getWishes(Long id) {
        System.out.println(wishRepository.findAllByUserId(id));
        return wishRepository.findAllByUserId(id);
    }

    /**
     * This method adds a new user's wish to the database
     * @param newWish - a Wishes object
     * @param user - a Users object
     */
    public void addWish(Wishes newWish, Users user) {
        newWish.setUserId(user.getId());
        wishRepository.save(newWish);
    }

    /**
     * This method gives selected wish
     * @param userId - user's ID in long type
     * @param wishName - name of user's wish in String type
     * @return return selected wish as Wishes object
     */
    public Wishes getSpecialWish(Long userId, String wishName){
        return wishRepository.findByUserIdAndWishName(userId, wishName);
    }

    /**
     * This method updates user's wish
     * @param wish - a Wishes object
     */
    public void updateWish(Wishes wish) {
        wishRepository.save(wish);
    }

    /**
     * This method deletes user's wish
     * @param wish - a Wishes object
     */
    public void deleteWish(Wishes wish) {
        wishRepository.deleteById(wish.getId());
    }
}
