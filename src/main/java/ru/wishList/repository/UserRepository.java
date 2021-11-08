package ru.wishList.repository;

import org.springframework.data.repository.CrudRepository;
import ru.wishList.models.Users;


public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByName(String name);
    boolean existsByName(String name);

}
