package ru.job4j.smapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.smapi.model.Follower;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.model.compoundkeys.FollowerKey;

import java.util.List;


public interface FollowerRepository extends CrudRepository<Follower, FollowerKey> {
}
