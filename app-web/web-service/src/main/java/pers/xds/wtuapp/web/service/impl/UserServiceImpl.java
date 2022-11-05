package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.service.UserService;
import pers.xds.wtuapp.web.service.bean.UserInfo;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.database.bean.WtuUserInfo;
import pers.xds.wtuapp.web.database.mapper.UserMapper;
import pers.xds.wtuapp.web.database.mapper.WtuUserInfoMapper;
import pers.xds.wtuapp.web.service.common.DataBeanCombiner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HuPeng
 * @date 2022-10-23 14:59
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private WtuUserInfoMapper wtuUserInfoMapper;

    private static final String[] USER_DATA_COL = new String[]{
            UserMapper.COLUMN_USER_ID,
            UserMapper.COLUMN_USERNAME,
            UserMapper.COLUMN_NICKNAME,
            UserMapper.COLUMN_CREDIT
    };

    @Autowired
    public void setWtuUserInfoMapper(WtuUserInfoMapper wtuUserInfoMapper) {
        this.wtuUserInfoMapper = wtuUserInfoMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#userId")
    public UserInfo queryUserInfo(int userId) {
        QueryWrapper<User> eq = new QueryWrapper<User>().select(USER_DATA_COL).eq(UserMapper.COLUMN_USER_ID, userId);
        User user = userMapper.selectOne(eq);
        if (user == null) {
            return null;
        }
        return new UserInfo(user, wtuUserInfoMapper.selectById(user.getUserId()));
    }

    private static final DataBeanCombiner<User, WtuUserInfo, UserInfo> USER_COMBINER =
            new DataBeanCombiner<>(UserInfo::new, (user, wtuUserInfo) -> user.getUserId().equals(wtuUserInfo.getUserId()));

    /**
     * @param ids 用户id 一次最多查30个, 若超出该长度会直接返回空数组
     */
    @Override
    public List<UserInfo> queryMultiUserInfo(List<Integer> ids) {
        final int maxLen = 30;
        if (ids.size() > maxLen || ids.isEmpty()) {
            return null;
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>()
                .select(USER_DATA_COL)
                .in(UserMapper.COLUMN_USER_ID, ids);
        List<User> userList = userMapper.selectList(userQueryWrapper);
        if (userList.isEmpty()) {
            return Collections.emptyList();
        }

        QueryWrapper<WtuUserInfo> wtuUserInfoWrapper = new QueryWrapper<WtuUserInfo>().in(WtuUserInfoMapper.COLUMN_USER_ID, ids);
        List<WtuUserInfo> wtuUserInfos = wtuUserInfoMapper.selectList(wtuUserInfoWrapper);
        return USER_COMBINER.combine(userList, wtuUserInfos);
    }
}
