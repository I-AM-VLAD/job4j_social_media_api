package ru.job4j.smapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.smapi.dto.frienddto.FriendRequestDto;
import ru.job4j.smapi.model.Friend;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.repository.FriendRepository;
import ru.job4j.smapi.security.userdetails.UserDetailsImpl;

import java.util.List;

public interface FriendService {

    Friend requestAtoB(FriendRequestDto friendRequestDto, UserDetailsImpl userDetails);
    List<Friend> getIncomingFriendRequests(UserDetailsImpl userDetails);
    boolean updateStatus(FriendRequestDto friendRequestDto, UserDetailsImpl userDetails, String newStatus);
    boolean deleteFriend(FriendRequestDto friendRequestDto, UserDetailsImpl userDetails);
}
