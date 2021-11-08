package ru.wishList.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wishList.models.Wishes;
import ru.wishList.repository.WishRepository;

@Service
public class WishService {

    @Autowired
    WishRepository wishRepository;

    //This method gets user's ID and returns its wishes
    public Iterable<Wishes> getWishes(Long id) {
        System.out.println(wishRepository.findAllByUserId(id));
        return wishRepository.findAllByUserId(id);
    }
}
