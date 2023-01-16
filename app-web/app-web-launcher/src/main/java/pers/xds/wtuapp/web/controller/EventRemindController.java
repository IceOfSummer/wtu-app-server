package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.EventRemindService;
import pers.xds.wtuapp.web.service.bean.CombinedEventRemind;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2023-01-14 16:14
 */
@RestController
@RequestMapping("remind")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class EventRemindController {

    private EventRemindService eventRemindService;

    @Autowired
    public void setEventRemindService(EventRemindService eventRemindService) {
        this.eventRemindService = eventRemindService;
    }

    @GetMapping("query")
    public ResponseTemplate<List<CombinedEventRemind>> queryEventRemind() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        List<CombinedEventRemind> combinedEventReminds = eventRemindService.queryUnreadRemind(userPrincipal.getId());
        return ResponseTemplate.success(combinedEventReminds);
    }

    @GetMapping("count")
    public ResponseTemplate<Integer> queryUnreadTipCount() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(eventRemindService.queryUnreadMessageCount(userPrincipal.getId()));
    }

    @PostMapping("read")
    public ResponseTemplate<Void> markAllRead() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        eventRemindService.markAllRead(userPrincipal.getId());
        return ResponseTemplate.success();
    }


}
