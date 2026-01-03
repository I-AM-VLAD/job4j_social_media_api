package ru.job4j.smapi.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.smapi.model.Follower;
import ru.job4j.smapi.model.Friend;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT user FROM User AS user"
            + " WHERE user.email = ?1 AND user.password = ?2")
    User findByEmailAndPassword(String email, String password);

    @Query("SELECT f.friendUser FROM Friend f WHERE f.user.id = :id")
    List<User> findFriendsByUserId(@Param("id") Integer id);

    @Query("SELECT f.followerUser FROM Follower f WHERE f.user.id = :id")
    List<User> findFollowersByUserId(@Param("id") Integer id);

    @Query("SELECT u FROM User u WHERE u.id IN :userIds")
    List<User> findUsers(@Param("userIds") List<Integer> userIds);


}
