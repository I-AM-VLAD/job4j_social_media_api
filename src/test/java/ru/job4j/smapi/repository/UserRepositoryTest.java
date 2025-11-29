package ru.job4j.smapi.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.smapi.model.User;


import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveUserThenFindById() {
        var user = new User();
        user.setName("Vlad");
        user.setEmail("vlad1@gmail.com");
        user.setPassword("1234");
        userRepository.save(user);

        var foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Vlad");
    }

    @Test
    public void whenFindAllThenReturnAllUsers() {
        var user = new User();
        user.setName("Vlad");
        user.setEmail("vlad1@gmail.com");
        user.setPassword("1234");
        userRepository.save(user);

        var user1 = new User();
        user1.setName("Oleg");
        user1.setEmail("oleg@gmail.com");
        user1.setPassword("1234");
        userRepository.save(user1);

        var users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getName).contains("Vlad", "Oleg");
    }
}