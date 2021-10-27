package ru.wishList.repository;

import org.springframework.data.repository.CrudRepository;
import ru.wishList.models.Wishes;

public interface WishRepository extends CrudRepository<Wishes, Long> {
    Iterable<Wishes> findAllByUserId(Long userId);
    Wishes findByUserIdAndWishName(Long userId, String wishName);
}
