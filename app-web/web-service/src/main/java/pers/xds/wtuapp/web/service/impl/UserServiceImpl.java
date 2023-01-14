package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.redis.EmailCaptchaCache;
import pers.xds.wtuapp.web.redis.bean.EmailData;
import pers.xds.wtuapp.web.service.EmailService;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.UserService;
import pers.xds.wtuapp.web.service.bean.BaseWtuUserInfo;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.database.mapper.UserMapper;
import pers.xds.wtuapp.web.service.config.email.EmailBindTemplateData;
import pers.xds.wtuapp.web.service.util.Random;

import java.util.*;

/**
 * @author HuPeng
 * @date 2022-10-23 14:59
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private EmailService emailService;

    private EmailCaptchaCache emailCaptchaCache;

    @Autowired
    public void setEmailCaptchaCache(EmailCaptchaCache emailCaptchaCache) {
        this.emailCaptchaCache = emailCaptchaCache;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }


    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User queryUserInfo(int userId) {
        return userMapper.selectByIdForQuery(userId);
    }


    /**
     * @param ids 用户id 一次最多查30个, 若超出该长度会直接返回空数组
     */
    @Override
    public List<User> queryMultiUserInfo(List<Integer> ids) {
        final int maxLength = 30;
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        if (ids.size() > maxLength) {
            return null;
        }
        return userMapper.selectAllByIds(ids);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userMapper.isUsernameExist(username) == 1;
    }

    public boolean isWtuUsernameExist(String wtuUsername) {
        return userMapper.isWtuIdExist(wtuUsername) == 1;
    }

    @Override
    public ServiceCode registerUser(String username, String password, String wtuId, BaseWtuUserInfo userInfo) {
        User user = new User(username, password, wtuId, userInfo.getName(), userInfo.getClassName());
        user.setNickname("用户" + username);
        userMapper.insert(user);
        return ServiceCode.SUCCESS;
    }

    @Override
    public User queryUserDetail(int uid) {
        return userMapper.selectUserDetail(uid);
    }

    @Override
    public ServiceCode updateUserInfo(int uid, User user) {
        user.setUserId(uid);
        int update = userMapper.update(user);
        return update == 1 ? ServiceCode.SUCCESS : ServiceCode.NOT_EXIST;
    }

    private static final int CAPTCHA_SIZE = 6;

    @Override
    public ServiceCode requireEmailUpdateCaptcha(int uid, String email) {
        String nickname = userMapper.selectNicknameOnly(uid);
        String captcha = Random.generateCaptcha(CAPTCHA_SIZE);

        emailCaptchaCache.saveEmailCaptcha(uid, email, captcha);
        EmailBindTemplateData data = new EmailBindTemplateData(nickname, captcha, "绑定邮箱");
        emailService.sendEmailCaptcha(email, data);
        return ServiceCode.SUCCESS;
    }

    @Override
    public ServiceCode updateEmail(int uid, String captcha) {
        if (captcha.length() != CAPTCHA_SIZE) {
            return ServiceCode.BAD_REQUEST;
        }
        EmailData emailCaptcha = emailCaptchaCache.getEmailCaptcha(uid);
        if (emailCaptcha == null) {
            return ServiceCode.BAD_REQUEST;
        }
        User user = new User();
        user.setUserId(uid);
        user.setEmail(emailCaptcha.email);
        userMapper.update(user);
        return ServiceCode.SUCCESS;
    }


}
