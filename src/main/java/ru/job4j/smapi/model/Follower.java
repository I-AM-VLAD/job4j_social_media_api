package ru.job4j.smapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.smapi.model.compoundkeys.FollowerKey;

import jakarta.persistence.*;

@Entity
@Table(name = "follower")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follower {

    @EmbeddedId
    private FollowerKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private User followerUser;
}
