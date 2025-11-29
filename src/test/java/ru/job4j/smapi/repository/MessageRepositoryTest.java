package ru.job4j.smapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.smapi.model.Message;
import ru.job4j.smapi.model.User;


import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveMessageThenFindById() {
        var userSender = new User();
        userSender.setName("Vlad");
        userSender.setEmail("vlad1@gmail.com");
        userSender.setPassword("1234");
        userRepository.save(userSender);

        var userReceiver = new User();
        userReceiver.setName("Oleg");
        userReceiver.setEmail("oleg@gmail.com");
        userReceiver.setPassword("1234");
        userRepository.save(userReceiver);

        var message = new Message();
        message.setUserSender(userSender);
        message.setUserReceiver(userReceiver);
        messageRepository.save(message);

        var foundMessage = messageRepository.findById(message.getId());
        assertThat(foundMessage).isPresent();
        assertThat(foundMessage.get().getUserSender()).isEqualTo(userSender);
    }

    @Test
    public void whenFindAllThenReturnAllMessages() {
        var userSender = new User();
        userSender.setName("Vlad");
        userSender.setEmail("vlad1@gmail.com");
        userSender.setPassword("1234");
        userRepository.save(userSender);

        var userReceiver = new User();
        userReceiver.setName("Oleg");
        userReceiver.setEmail("oleg@gmail.com");
        userReceiver.setPassword("1234");
        userRepository.save(userReceiver);

        var message = new Message();
        message.setUserSender(userSender);
        message.setUserReceiver(userReceiver);
        messageRepository.save(message);

        var userSender1 = new User();
        userSender1.setName("Stas");
        userSender1.setEmail("stas2@gmail.com");
        userSender1.setPassword("1234");
        userRepository.save(userSender1);

        var userReceiver1 = new User();
        userReceiver1.setName("Andrew");
        userReceiver1.setEmail("andrew2@gmail.com");
        userReceiver1.setPassword("1234");
        userRepository.save(userReceiver1);

        var message1 = new Message();
        message1.setUserSender(userSender1);
        message1.setUserReceiver(userReceiver1);
        messageRepository.save(message1);

        var messages = messageRepository.findAll();
        assertThat(messages).hasSize(2);
        assertThat(messages).extracting(Message::getUserSender).contains(userSender, userSender1);
    }
}