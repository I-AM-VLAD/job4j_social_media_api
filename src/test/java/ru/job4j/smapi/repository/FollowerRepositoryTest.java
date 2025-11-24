package ru.job4j.smapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.smapi.model.Follower;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.model.compoundkeys.FollowerKey;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FollowerRepositoryTest {

    @Autowired
    private  FollowerRepository followerRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        followerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveFollowerThenFindById() {
        var user = new User();
        user.setName("Vlad");
        user.setEmail("vlad1@gmail.com");
        user.setPassword("1234");
        userRepository.save(user);

        var userFollower = new User();
        userFollower.setName("Oleg");
        userFollower.setEmail("oleg@gmail.com");
        userFollower.setPassword("1234");
        userRepository.save(userFollower);

        var followerKey = new FollowerKey();
        followerKey.setUserId(user.getId());
        followerKey.setFollowerId(userFollower.getId());

        var follower = new Follower();
        follower.setId(followerKey);
        follower.setUser(user);
        follower.setFollowerUser(userFollower);

        followerRepository.save(follower);
        var foundFollower = followerRepository.findById(follower.getId());
        assertThat(foundFollower).isPresent();
        assertThat(foundFollower.get().getFollowerUser()).isEqualTo(userFollower);
    }

    @Test
    public void whenFindAllThenReturnAllFollowers() {
        var user = new User();
        user.setName("Vlad");
        user.setEmail("vlad2@gmail.com");
        user.setPassword("1234");
        userRepository.save(user);

        var userFollower = new User();
        userFollower.setName("Oleg");
        userFollower.setEmail("oleg2@gmail.com");
        userFollower.setPassword("1234");
        userRepository.save(userFollower);

        var followerKey = new FollowerKey();
        followerKey.setUserId(user.getId());
        followerKey.setFollowerId(userFollower.getId());

        var follower = new Follower();
        follower.setId(followerKey);
        follower.setUser(user);
        follower.setFollowerUser(userFollower);
        followerRepository.save(follower);

        var user1 = new User();
        user1.setName("Stas");
        user1.setEmail("stas@gmail.com");
        user1.setPassword("1234");
        userRepository.save(user1);

        var userFollower1 = new User();
        userFollower1.setName("Andrew");
        userFollower1.setEmail("andrew@gmail.com");
        userFollower1.setPassword("1234");
        userRepository.save(userFollower1);

        var followerKey1 = new FollowerKey();
        followerKey1.setUserId(user1.getId());
        followerKey1.setFollowerId(userFollower1.getId());

        var follower1 = new Follower();
        follower1.setId(followerKey1);
        follower1.setUser(user1);
        follower1.setFollowerUser(userFollower1);
        followerRepository.save(follower1);

        var followers = followerRepository.findAll();
        assertThat(followers).hasSize(2);
        assertThat(followers).extracting(Follower::getUser).contains(user, user1);
    }
}