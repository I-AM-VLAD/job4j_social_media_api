package ru.job4j.smapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import ru.job4j.smapi.model.Friend;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.model.compoundkeys.FriendKey;

import java.util.List;

public interface FriendRepository extends CrudRepository<Friend, FriendKey> {
}
