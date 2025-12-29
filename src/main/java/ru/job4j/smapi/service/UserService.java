package ru.job4j.smapi.service;

import ru.job4j.smapi.model.User;

public interface UserService {
    User save(User user);
    Boolean deleteById(Integer id);
    Boolean update(User userUpdate);
}
