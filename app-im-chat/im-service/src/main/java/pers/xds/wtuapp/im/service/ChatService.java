package pers.xds.wtuapp.im.service;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2022-09-03 16:39
 */
@Service
public interface ChatService {

    /**
     * 保存离线消息
     * @param content 消息内容
     * @param sender 谁发的
     * @param to 发给谁
     * @throws NoSuchElementException 未找到该消息对应该用户
     */
    void saveOfflineMessage(String content, int sender, int to) throws NoSuchElementException;

}
