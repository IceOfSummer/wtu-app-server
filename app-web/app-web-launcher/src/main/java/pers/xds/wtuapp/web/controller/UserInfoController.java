package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.web.service.UserInfoService;
import pers.xds.wtuapp.web.service.bean.UserInfo;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.util.StringUtils;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-16 22:15
 */
@RestController
@RequestMapping("/user/info")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class UserInfoController {

    private UserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }
    /**
     * 查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("{id}")
    public ResponseTemplate<UserInfo> queryUserInfo(@PathVariable int id) {
        return ResponseTemplate.success(userInfoService.queryUserInfo(id));
    }

    /**
     * 查询多个用户的信息
     * @param uid 用户id
     * @return 多个用户的信息
     */
    @GetMapping("/multi_query")
    public ResponseTemplate<List<UserInfo>> queryMultiUserInfo(@RequestParam(name = "i") String uid) {
        final char splitter = ',';
        final int maxSize = 30;
        List<Integer> integers = StringUtils.parseLineString(uid, splitter, maxSize);
        if (integers == null) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        List<UserInfo> userInfoViews = userInfoService.queryMultiUserInfo(integers);
        if (userInfoViews == null) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        return ResponseTemplate.success(userInfoViews);
    }


}
