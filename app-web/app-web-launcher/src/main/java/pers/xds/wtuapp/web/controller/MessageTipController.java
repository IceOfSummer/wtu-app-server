package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.bean.MessageTip;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.MessageTipService;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2023-01-11 14:00
 */
@RestController
@RequestMapping("message")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class MessageTipController {

    private MessageTipService messageTipService;

    @Autowired
    public void setMessageTipService(MessageTipService messageTipService) {
        this.messageTipService = messageTipService;
    }

    @GetMapping("tips")
    public ResponseTemplate<List<MessageTip>> queryMessageTip(@RequestParam("m") int midId) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(messageTipService.queryMessageTip(userPrincipal.getId(), midId));
    }

}
