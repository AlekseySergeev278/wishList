package ru.wishList.repository;

import org.springframework.data.repository.CrudRepository;
import ru.wishList.models.Friends;

public interface FriendRepository extends CrudRepository<Friends, Long> {
    Iterable<Friends> findAllByUserId(Long userId);

}
