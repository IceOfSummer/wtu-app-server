package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.database.bean.MessageTip;
import pers.xds.wtuapp.web.database.mapper.MessageTipMapper;
import pers.xds.wtuapp.web.service.MessageTipService;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2023-01-11 13:58
 */
@Service
public class MessageTipServiceImpl implements MessageTipService {

    private MessageTipMapper messageTipMapper;

    @Autowired
    public void setMessageTipMapper(MessageTipMapper messageTipMapper) {
        this.messageTipMapper = messageTipMapper;
    }

    @Override
    @Async
    public void sendTipMessage(MessageTip messageTip) {
        messageTipMapper.insert(messageTip);
    }

    @Override
    public List<MessageTip> queryMessageTip(int uid, int minId) {
        final int MAX_SIZE = 30;
        return messageTipMapper.selectMessageTips(uid, minId, MAX_SIZE);
    }

}
