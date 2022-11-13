package pers.xds.wtuapp.im.redis.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.redis.MessageCache;

import java.util.List;


@SpringBootTest
class MessageCacheImplTest {

    private MessageCache messageCache;

    @Autowired
    public void setMessageCache(MessageCache messageCache) {
        this.messageCache = messageCache;
    }


    @Test
    void testSaveAndQuery() {
        final int receiver = 1;
        final int sender = 2;
        Message hello = new Message(receiver, sender, "hello");
        hello.setMsgId(1);
        messageCache.saveMessage(hello, 4, 9);
        List<String> ids = List.of("4");
        Assertions.assertEquals(messageCache.queryCachedMessage(1, ids).size(), ids.size());
    }


}