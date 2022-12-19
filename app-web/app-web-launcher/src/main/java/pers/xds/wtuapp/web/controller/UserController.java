package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.bean.RegisterParam;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.database.group.UpdateGroup;
import pers.xds.wtuapp.web.security.PwdEncoder;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.ServiceCodeWrapper;
import pers.xds.wtuapp.web.service.UserService;
import pers.xds.wtuapp.web.service.WtuAuthService;
import pers.xds.wtuapp.web.service.bean.BaseWtuUserInfo;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.service.bean.WtuAuthInitParam;
import pers.xds.wtuapp.web.service.bean.WtuAuthParam;
import pers.xds.wtuapp.web.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-16 22:15
 */
@RestController
@RequestMapping("user")
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
public class UserController {

    private UserService userService;

    private WtuAuthService wtuAuthService;

    @Autowired
    public void setWtuAuthService(WtuAuthService wtuAuthService) {
        this.wtuAuthService = wtuAuthService;
    }

    @Autowired
    public void setUserInfoService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/info/{id}")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<User> queryUserInfo(@PathVariable int id) {
        return ResponseTemplate.success(userService.queryUserInfo(id));
    }

    /**
     * 查询多个用户的信息
     * @param uid 用户id
     * @return 多个用户的信息
     */
    @GetMapping("/info/multi_query")
    public ResponseTemplate<List<User>> queryMultiUserInfo(@RequestParam(name = "i") String uid) {
        final char splitter = ',';
        final int maxSize = 30;
        List<Integer> integers = StringUtils.parseLineString(uid, splitter, maxSize);
        if (integers == null) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        List<User> userInfoViews = userService.queryMultiUserInfo(integers);
        if (userInfoViews == null) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        return ResponseTemplate.success(userInfoViews);
    }

    @PostMapping("update")
    public ResponseTemplate<Void> updateUserInfo(@Validated(UpdateGroup.class) User user) {
        if (user.getNickname() == null) {
            return ResponseTemplate.success();
        }
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        ServiceCode info = userService.updateUserInfo(userPrincipal.getId(), user);
        return info == ServiceCode.SUCCESS ? ResponseTemplate.success() : ResponseTemplate.fail(ResponseCode.ELEMENT_NOT_EXIST);
    }

    @PostMapping("/update/email/captcha")
    public ResponseTemplate<Void> sendEmailUpdateCaptcha(@RequestParam("e") String email) {
        if (StringUtils.isInvalidEmail(email)) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        int id = userPrincipal.getId();
        ServiceCode code = userService.requireEmailUpdateCaptcha(id, email);
        if (code == ServiceCode.SUCCESS) {
            return ResponseTemplate.success();
        }
        return ResponseTemplate.fail(ResponseCode.RATE_LIMIT);
    }

    @PostMapping("/update/email/bind")
    public ResponseTemplate<Void> updateEmail(@RequestParam("c") String captcha) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        ServiceCode code = userService.updateEmail(userPrincipal.getId(), captcha);
        if (code == ServiceCode.SUCCESS) {
            return ResponseTemplate.success();
        }
        return ResponseTemplate.fail(ResponseCode.WRONG_CREDENTIALS);
    }


    final int USERNAME_MIN_LENGTH = 8;

    final int USERNAME_MAX_LENGTH = 25;

    /**
     * 获取注册初始化参数
     * @param username 要注册的用户名
     */
    @GetMapping("/register/init")
    public ResponseTemplate<WtuAuthInitParam> getRegisterInitParam(@RequestParam("u") String username) {
        if (username.length() < USERNAME_MIN_LENGTH || username.length() > USERNAME_MAX_LENGTH) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }

        boolean exist = userService.isUsernameExist(username);
        if (exist) {
            return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE, "用户名已经存在");
        }
        try {
            ServiceCodeWrapper<WtuAuthInitParam> auth = wtuAuthService.initAuth();
            if (auth.code == ServiceCode.SUCCESS) {
                return ResponseTemplate.success(auth.data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseTemplate.fail(ResponseCode.SERVER_BUSY);
    }

    /**
     * 注册
     */
    @PostMapping("/register/do")
    public ResponseTemplate<Void> register(@Validated RegisterParam registerParam) {
        if (userService.isWtuUsernameExist(registerParam.wtuUsername)) {
            return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE, "该学号已被注册");
        }
        if (userService.isUsernameExist(registerParam.registerUsername)) {
            return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE, "用户名已存在");
        }
        WtuAuthParam wtuAuthParam = new WtuAuthParam(
                registerParam.wtuUsername,
                registerParam.wtuPassword,
                registerParam.wtuCaptcha,
                registerParam.lt,
                registerParam.sid,
                registerParam.route
        );
        BaseWtuUserInfo userInfo;
        try {
            ServiceCodeWrapper<BaseWtuUserInfo> auth = wtuAuthService.auth(wtuAuthParam);
            userInfo = auth.data;
            if (auth.code != ServiceCode.SUCCESS) {
                if (auth.code == ServiceCode.BAD_REQUEST) {
                    return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE, "用户名或密码或验证码错误");
                } else  {
                    return ResponseTemplate.fail(ResponseCode.SERVER_BUSY);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseTemplate.fail(ResponseCode.SERVER_BUSY);
        }
        ServiceCode serviceCode = userService.registerUser(
                registerParam.registerUsername,
                PwdEncoder.BCrypt.encode(registerParam.registerPassword),
                registerParam.wtuUsername,
                userInfo
        );
        if (serviceCode == ServiceCode.SUCCESS) {
            return ResponseTemplate.success();
        }
        return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE, "用户名已被注册");
    }

}
