package ru.job4j.smapi.model.compoundkeys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerKey {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "follower_id")
    private Integer followerId;
}
