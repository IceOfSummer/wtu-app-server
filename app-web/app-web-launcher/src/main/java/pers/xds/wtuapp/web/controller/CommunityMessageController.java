package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;
import pers.xds.wtuapp.web.database.group.InsertGroup;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.CommunityService;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.ServiceCodeWrapper;
import pers.xds.wtuapp.web.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author DeSen Xu
 * @date 2022-12-10 17:02
 */
@RestController
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
@RequestMapping("community")
public class CommunityMessageController {

    private CommunityService communityService;

    @Autowired
    public void setCommunityService(CommunityService communityService) {
        this.communityService = communityService;
    }


    /**
     * 发布一个消息(帖子)
     * @param communityMessage 消息内容
     */
    @PostMapping("post")
    public ResponseTemplate<Integer> postArticle(@Validated(InsertGroup.class) CommunityMessage communityMessage) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        ServiceCodeWrapper<Integer> wrapper = communityService.postMessage(userPrincipal.getId(), communityMessage);
        if (wrapper.code == ServiceCode.SUCCESS) {
            return ResponseTemplate.success(wrapper.data);
        }
        return ResponseTemplate.fail(ResponseCode.RATE_LIMIT);
    }

    /**
     * 获取最新消息. 若不传，则默认获取最新消息，若两个都传，默认使用maxId
     * @param maxId 消息最大id
     * @param minId 最小消息id
     * @return 最新消息
     */
    @GetMapping("newly_message")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<List<Map<String, String>>> queryNewlyCommunityMessage(
            @RequestParam(value = "mx", required = false) Integer maxId,
            @RequestParam(value = "mi", required = false) Integer minId
    ) {
        if (maxId == null && minId == null) {
            return ResponseTemplate.success(communityService.queryNewlyCommunityMessage(null));
        } else if (maxId != null) {
            return ResponseTemplate.success(communityService.queryNewlyCommunityMessage(maxId));
        } else {
            return ResponseTemplate.success(communityService.queryNewlyCommunityMessageByMinId(minId));
        }
    }

    /**
     * 查询一级评论或二级评论
     * @param pid 根文章id
     * @param page 第几页
     * @param size 每页大小
     */
    @GetMapping("/reply/query")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<List<Map<String, String>>> queryReply(
            @RequestParam("pi") int pid,
            @RequestParam(value = "p", required = false, defaultValue = "1") int page,
            @RequestParam(value = "s", required = false, defaultValue = "5") int size
            ) {
        return ResponseTemplate.success(communityService.queryMessageReply(pid, page, size));
    }

    /**
     * 查询一级评论下的部分二级评论
     * @param pids 要查询的一级评论，每个id以逗号分隔，如: `1,2,3,4,5`
     */
    @GetMapping("/reply/preview")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<List<Map<String, String>>> querySubReplyPreview(@RequestParam("p") String pids) {
        final int maxLen = 10;
        List<Integer> list = StringUtils.parseLineString(pids, ',', maxLen);
        if (list == null) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        if (list.isEmpty()) {
            return ResponseTemplate.success(Collections.emptyList());
        }
        return ResponseTemplate.success(communityService.querySubReplyPreview(list));
    }

    /**
     * 点赞/踩某条消息
     * @param attitude 用户的态度, 0:踩, 1: 赞, null: 无评价(用来取消点赞/踩)
     */
    @PostMapping("/feedback")
    public ResponseTemplate<Void> feedbackMessage(
            @RequestParam("i") int messageId,
            @RequestParam(value = "l", required = false) Integer attitude
    ) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        Boolean thumbsUp;
        if (attitude == null) {
            thumbsUp = null;
        } else {
            thumbsUp = attitude == 1;
        }
        ServiceCode serviceCode = communityService.feedbackMessage(userPrincipal.getId(), messageId, thumbsUp);
        if (serviceCode == ServiceCode.SUCCESS) {
            return ResponseTemplate.success();
        }
        if (serviceCode == ServiceCode.NOT_AVAILABLE) {
            return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE, "您已经点过了");
        }
        return ResponseTemplate.fail(ResponseCode.RATE_LIMIT);
    }

}
