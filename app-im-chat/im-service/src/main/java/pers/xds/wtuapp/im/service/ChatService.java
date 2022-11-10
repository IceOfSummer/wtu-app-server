package pers.xds.wtuapp.im.service;

import org.springframework.stereotype.Service;


/**
 * @author DeSen Xu
 * @date 2022-09-03 16:39
 */
@Service
public interface ChatService {

    /**
     * 保存消息
     * @param content 消息内容
     * @param sender 谁发的
     * @param to 发给谁
     * @param sync 是否需要同步. 若用户在线，则应该设置为true以加快消息漏发时的恢复
     * @return 消息id
     */
    int saveMessage(String content, int sender, int to, boolean sync);

}
