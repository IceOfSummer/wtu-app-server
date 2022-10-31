package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.bean.Message;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.MessageService;

import java.util.List;

/**
 * 消息web接口
 * @author DeSen Xu
 * @date 2022-10-31 15:37
 */
@RestController
@RequestMapping("message")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class MessageController {

    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 获取离线消息
     * @param receivedId 从哪里开始接收
     * @return 离线消息
     */
    @GetMapping("offline")
    public ResponseTemplate<List<Message>> getOfflineMessage(@RequestParam("r") int receivedId) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(messageService.getOfflineMessage(userPrincipal.getId(), receivedId));
    }

    /**
     * 标记到maxId的消息都已经收到
     * @param maxId 已经接收到的最大消息id
     * @return void
     */
    @PostMapping("mark")
    public ResponseTemplate<Void> markMessageReceived(@RequestParam("m") int maxId) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        messageService.markMessageReceived(userPrincipal.getId(), maxId);
        return ResponseTemplate.success();
    }

    /**
     * 获取消息接收状态
     * @return 消息接收状态
     */
    @GetMapping("status")
    public ResponseTemplate<Integer> getMessageReceivedId() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(messageService.getMessageReceivedId(userPrincipal.getId()));
    }

}
