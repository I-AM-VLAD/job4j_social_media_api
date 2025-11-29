package ru.job4j.smapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.smapi.model.Follower;
import ru.job4j.smapi.model.Friend;
import ru.job4j.smapi.model.User;
import ru.job4j.smapi.model.compoundkeys.FollowerKey;
import ru.job4j.smapi.model.compoundkeys.FriendKey;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendRepositoryTest {
    @Autowired
    private  FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        friendRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveFriendThenFindById() {
        var user = new User();
        user.setName("Vlad");
        user.setEmail("vlad3@gmail.com");
        user.setPassword("1234");
        userRepository.save(user);

        var userFriend = new User();
        userFriend.setName("Oleg");
        userFriend.setEmail("oleg3@gmail.com");
        userFriend.setPassword("1234");
        userRepository.save(userFriend);

        var friendKey = new FriendKey();
        friendKey.setUserId(user.getId());
        friendKey.setFriendId(userFriend.getId());

        var friend = new Friend();
        friend.setId(friendKey);
        friend.setUser(user);
        friend.setFriendUser(userFriend);
        friendRepository.save(friend);

        var foundFriend = friendRepository.findById(friend.getId());
        assertThat(foundFriend).isPresent();
        assertThat(foundFriend.get().getFriendUser()).isEqualTo(userFriend);
    }

    @Test
    public void whenFindAllThenReturnAllFriends() {
        var user = new User();
        user.setName("Vlad");
        user.setEmail("vlad4@gmail.com");
        user.setPassword("1234");
        userRepository.save(user);

        var userFriend = new User();
        userFriend.setName("Oleg");
        userFriend.setEmail("oleg4@gmail.com");
        userFriend.setPassword("1234");
        userRepository.save(userFriend);

        var friendKey = new FriendKey();
        friendKey.setUserId(user.getId());
        friendKey.setFriendId(userFriend.getId());

        var friend = new Friend();
        friend.setId(friendKey);
        friend.setUser(user);
        friend.setFriendUser(userFriend);
        friendRepository.save(friend);

        var user1 = new User();
        user1.setName("Stas");
        user1.setEmail("stas2@gmail.com");
        user1.setPassword("1234");
        userRepository.save(user1);

        var userFriend1 = new User();
        userFriend1.setName("Andrew");
        userFriend1.setEmail("andrew2@gmail.com");
        userFriend1.setPassword("1234");
        userRepository.save(userFriend1);

        var friendKey1 = new FriendKey();
        friendKey1.setUserId(user1.getId());
        friendKey1.setFriendId(userFriend1.getId());

        var friend1 = new Friend();
        friend1.setId(friendKey1);
        friend1.setUser(user1);
        friend1.setFriendUser(userFriend1);
        friendRepository.save(friend1);

        var friends = friendRepository.findAll();
        assertThat(friends).hasSize(2);
        assertThat(friends).extracting(Friend::getUser).contains(user, user1);
    }
}