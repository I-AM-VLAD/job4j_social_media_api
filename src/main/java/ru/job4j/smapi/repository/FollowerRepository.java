package ru.job4j.smapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.smapi.model.Follower;
import ru.job4j.smapi.model.compoundkeys.FollowerKey;


public interface FollowerRepository extends CrudRepository<Follower, FollowerKey> {
}
