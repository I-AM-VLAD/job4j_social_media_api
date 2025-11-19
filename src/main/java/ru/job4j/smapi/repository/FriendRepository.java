package ru.job4j.smapi.repository;

import org.springframework.data.repository.CrudRepository;

import ru.job4j.smapi.model.Friend;
import ru.job4j.smapi.model.compoundkeys.FriendKey;

public interface FriendRepository extends CrudRepository<Friend, FriendKey> {
}
