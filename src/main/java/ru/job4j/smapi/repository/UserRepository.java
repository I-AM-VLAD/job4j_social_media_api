package ru.job4j.smapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
}
