package ru.job4j.smapi.service;

import ru.job4j.smapi.dto.UserDto;
import ru.job4j.smapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(UserDto userDto);
    Boolean deleteById(Integer id);
    Boolean update(UserDto userUpdateDto);
    Optional<User> getById(Integer id);
    List<User> findUsers(List<Integer> userIds);
}
