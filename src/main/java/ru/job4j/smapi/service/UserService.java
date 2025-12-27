package ru.job4j.smapi.service;

import ru.job4j.smapi.model.User;

public interface UserService {
    User save(User user);
    void deleteById(Integer id);
    void update(User userUpdate);
}
