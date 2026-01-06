package ru.job4j.smapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.smapi.dto.UserDto;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(UserDto userDto) {
        User user = fromDtoToUser(userDto);
        return userRepository.save(user);
    }

    public Boolean deleteById(Integer id) {
        boolean flag = false;
        userRepository.deleteById(id);
        if (userRepository.findById(id).isEmpty()) {
            flag = true;
        }
        return flag;
    }

    public Boolean update(UserDto userUpdateDto) {
        boolean flag = false;
        if (userUpdateDto != null) {
            User user = userRepository.findById(userUpdateDto.getId()).orElseThrow();
            user.setName(userUpdateDto.getName());
            userRepository.save(user);
            flag = true;
        }
        return flag;
    }

    public Optional<User> getById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> findUsers(List<Integer> userIds) {
        return userRepository.findUsers(userIds);
    }

    public User fromDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setPosts(userDto.getPosts());

        return user;
    }
}
