package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.aop.RateLimit;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.bean.CommunityMessage;
import pers.xds.wtuapp.web.database.group.InsertGroup;
import pers.xds.wtuapp.web.database.view.CommunityMessagePost;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.CommunityService;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.ServiceCodeWrapper;
import pers.xds.wtuapp.web.service.bean.PostReply;
import pers.xds.wtuapp.web.util.StringUtils;

import java.util.Collection;
import java.util.List;

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
     * 用户每天最多可以发送消息的次数
     */
    public static final int POST_MESSAGE_ALLOW_RATE = 100;

    /**
     * 发布一个消息(帖子)
     *
     * @param communityMessage 消息内容
     */
    @RateLimit(value = POST_MESSAGE_ALLOW_RATE, limitMessage = "您每天最多发送" + POST_MESSAGE_ALLOW_RATE + "次消息")
    @PostMapping("post")
    public ResponseTemplate<Integer> postArticle(@Validated(InsertGroup.class) CommunityMessage communityMessage, @RequestParam(value = "n",required = false) String notification) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        boolean enableNotification = StringUtils.isTrue(notification);
        ServiceCodeWrapper<Integer> wrapper = communityService.postMessage(userPrincipal.getId(), communityMessage, enableNotification);
        if (wrapper.code == ServiceCode.SUCCESS) {
            return ResponseTemplate.success(wrapper.data);
        }
        return ResponseTemplate.fail(ResponseCode.UNKNOWN_ERROR);
    }

    /**
     * 获取最新消息. 若不传，则默认获取最新消息，若两个都传，默认使用maxId
     *
     * @param maxId 消息最大id
     * @param minId 最小消息id
     * @return 最新消息
     */
    @GetMapping("newly_message")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<List<CommunityMessagePost>> queryNewlyCommunityMessage(
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
     * 根据id查询消息信息
     * @param id 消息id
     */
    @GetMapping("/article/{id}")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<CommunityMessagePost> queryPostById(@PathVariable int id) {
        return ResponseTemplate.success(communityService.queryMessageById(id));
    }

    /**
     * 查询一级评论或二级评论
     *
     * @param id  根文章id
     * @param page 第几页
     * @param size 每页大小
     */
    @GetMapping("/article/{id}/reply/level")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<List<CommunityMessagePost>> queryReplyWithLevel(
            @PathVariable int id,
            @RequestParam(value = "p", required = false, defaultValue = "1") int page,
            @RequestParam(value = "s", required = false, defaultValue = "5") int size
    ) {
        return ResponseTemplate.success(communityService.queryMessageReply(id, page, size));
    }

    /**
     * 查询帖子的一级和二级评论
     * @param id 消息id
     * @param page 查询第几页
     * @param size 每页大小
     */
    @GetMapping("/article/{id}/reply")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<PostReply> queryPostReply(@PathVariable int id,
                                                      @RequestParam(value = "p", required = false, defaultValue = "1") int page,
                                                      @RequestParam(value = "s", required = false, defaultValue = "5") int size) {
        PostReply postReply = communityService.queryPostReply(id, page, size);
        if (postReply == null) {
            return ResponseTemplate.fail(ResponseCode.ELEMENT_NOT_EXIST);
        }
        return ResponseTemplate.success(postReply);
    }

    @PostMapping("/article/{id}/delete")
    public ResponseTemplate<Void> deletePost(@PathVariable int id) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        communityService.deleteMessage(userPrincipal.getId(), id);
        return ResponseTemplate.success();
    }

    /**
     * 用户使用反馈功能的最大次数
     */
    public static final int FEEDBACK_MAX_ALLOW_RATE = 100;

    /**
     * 点赞/踩某条消息
     *
     * @param attitude 用户的态度, 0:踩, 1: 赞, null: 无评价(用来取消点赞/踩)
     */
    @PostMapping("/feedback")
    @RateLimit(value = FEEDBACK_MAX_ALLOW_RATE, limitMessage = "您每天最多点" + FEEDBACK_MAX_ALLOW_RATE + "次赞")
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
        return ResponseTemplate.fail(ResponseCode.UNKNOWN_ERROR);
    }

    /**
     * 查询置顶消息
     */
    @GetMapping("top")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<Collection<Object>> queryTopMessage() {
        return ResponseTemplate.success(communityService.queryPinedMessage());
    }

    /**
     * 置顶消息
     */
    @PostMapping("/article/{id}/pin")
    @PreAuthorize(SecurityConstant.EL_ADMIN_ONLY)
    public ResponseTemplate<Void> pinMessage(@PathVariable("id") int messageId) {
        ServiceCode serviceCode = communityService.pinMessage(messageId);
        if (serviceCode == ServiceCode.SUCCESS) {
            return ResponseTemplate.success();
        }
        // not exist
        return ResponseTemplate.fail(ResponseCode.ELEMENT_NOT_EXIST);
    }

    /**
     * 取消置顶
     * @return 永远返回成功，即使消息没有被置顶
     */
    @PostMapping("/article/{id}/unpin")
    @PreAuthorize(SecurityConstant.EL_ADMIN_ONLY)
    public ResponseTemplate<Void> unpinMessage(@PathVariable("id") int messageId) {
        communityService.cancelPin(messageId);
        return ResponseTemplate.success();
    }

}
