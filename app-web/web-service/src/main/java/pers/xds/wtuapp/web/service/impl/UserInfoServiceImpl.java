package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.service.UserInfoService;
import pers.xds.wtuapp.web.service.bean.UserInfo;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.database.bean.WtuUserInfo;
import pers.xds.wtuapp.web.database.mapper.UserMapper;
import pers.xds.wtuapp.web.database.mapper.WtuUserInfoMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HuPeng
 * @date 2022-10-23 14:59
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserMapper userMapper;

    private WtuUserInfoMapper wtuUserInfoMapper;

    @Autowired
    public void setWtuUserInfoMapper(WtuUserInfoMapper wtuUserInfoMapper) {
        this.wtuUserInfoMapper = wtuUserInfoMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserInfo queryUserInfo(int userId) {
        User user = selectUserById(userId);
        if (user == null) {
            return null;
        }
        WtuUserInfo wtuUserInfo = selectWtuUserByUsername(user.getUsername());
        if (wtuUserInfo == null) {
            return null;
        }
        return new UserInfo(user, wtuUserInfo);
    }

    @Cacheable(cacheNames = "user", key = "#userId")
    @Nullable
    public User selectUserById(int userId) {
        return userMapper.selectById(userId);
    }

    @Cacheable(cacheNames = "wtuUser", key = "#username")
    public WtuUserInfo selectWtuUserByUsername(String username) {
        return wtuUserInfoMapper.selectById(username);
    }

    /**
     * @param ids 用户id 一次最多查30个, 若超出该长度会直接返回空数组
     */
    @Override
    public List<UserInfo> queryMultiUserInfo(List<Integer> ids) {
        final int maxLen = 30;
        if (ids.size() > maxLen || ids.isEmpty()) {
            return null;
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().in(UserMapper.COLUMN_USER_ID, ids);
        List<User> userList = userMapper.selectList(userQueryWrapper);
        if (userList.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> collect = userList.stream().map(User::getWtuUsername).collect(Collectors.toList());

        QueryWrapper<WtuUserInfo> wtuUserInfoWrapper = new QueryWrapper<WtuUserInfo>().in(WtuUserInfoMapper.COLUMN_WTU_USERNAME, collect);
        List<WtuUserInfo> wtuUserInfos = wtuUserInfoMapper.selectList(wtuUserInfoWrapper);
        ArrayList<UserInfo> userInfos = new ArrayList<>(userList.size());

        // userList的长度大于等于userInfos的长度
        Iterator<User> uIterator = userList.iterator();
        Iterator<WtuUserInfo> wIterator = wtuUserInfos.iterator();
        WtuUserInfo unspent = null;
        while (uIterator.hasNext()) {
            User next = uIterator.next();
            if (unspent == null) {
                unspent = wIterator.next();
            }
            if (unspent.getUserId().equals(next.getUserId())) {
                userInfos.add(new UserInfo(next, unspent));
                unspent = null;
            } else {
                userInfos.add(new UserInfo(next, null));
            }
        }

        return userInfos;
    }
}
