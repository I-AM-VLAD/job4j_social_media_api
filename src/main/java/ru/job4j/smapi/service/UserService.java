package ru.job4j.smapi.service;

import ru.job4j.smapi.model.User;

import java.util.Optional;

public interface UserService {
    User save(User user);
    Boolean deleteById(Integer id);
    Boolean update(User userUpdate);
    Optional<User> getById(Integer id);
}
