package ru.job4j.smapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.smapi.model.Friend;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.repository.FriendRepository;

public interface FollowerService {

    void createFriend(String status, User user, User userFriend);
    void deleteFriend(Friend friend);
}
