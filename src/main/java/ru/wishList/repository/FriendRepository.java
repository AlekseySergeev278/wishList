package ru.wishList.repository;

import org.springframework.data.repository.CrudRepository;
import ru.wishList.models.Friends;
import ru.wishList.models.Users;

public interface FriendRepository extends CrudRepository<Friends, Long> {
    Iterable<Friends> findAllByUserId(Long userId);


}
