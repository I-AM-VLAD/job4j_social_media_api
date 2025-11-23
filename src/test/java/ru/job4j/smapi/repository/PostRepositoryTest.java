package ru.job4j.smapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.job4j.smapi.model.Message;
import ru.job4j.smapi.model.Post;
import ru.job4j.smapi.model.User;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void whenSavePostThenFindById() {
        var user = new User();
        user.setName("Vlad");
        user.setEmail("vlad1@gmail.com");
        user.setPassword("1234");
        userRepository.save(user);

        var post = new Post();
        post.setUser(user);
        post.setTitle("title");
        post.setContent("content");
        post.setImageUrl("image");
        post.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        postRepository.save(post);

        var foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getUser()).isEqualTo(user);
    }

    @Test
    public void whenFindAllThenReturnAllPosts() {
        var user = new User();
        user.setName("Vlad");
        user.setEmail("vlad1@gmail.com");
        user.setPassword("1234");
        userRepository.save(user);

        var post = new Post();
        post.setUser(user);
        post.setTitle("title");
        post.setContent("content");
        post.setImageUrl("image");
        post.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        postRepository.save(post);

        var user1 = new User();
        user1.setName("Oleg");
        user1.setEmail("oleg@gmail.com");
        user1.setPassword("1234");
        userRepository.save(user1);

        var post1 = new Post();
        post1.setUser(user1);
        post1.setTitle("title1");
        post1.setContent("content1");
        post1.setImageUrl("image1");
        post1.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
        postRepository.save(post1);

        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getUser).contains(user, user1);
    }
}