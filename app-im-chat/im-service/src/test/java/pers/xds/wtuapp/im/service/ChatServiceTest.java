package pers.xds.wtuapp.im.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.xds.wtuapp.im.database.bean.Message;


@SpringBootTest
class ChatServiceTest {

    private ChatService chatService;

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Test
    void saveMessage() {
        // 2发给1
        Message msg = new Message(1, 2, "hello-" + System.currentTimeMillis());
        chatService.saveMessage(msg, true);
    }
}