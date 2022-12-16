package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.UserService;
import pers.xds.wtuapp.web.service.bean.BaseWtuUserInfo;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.database.mapper.UserMapper;

import java.util.*;

/**
 * @author HuPeng
 * @date 2022-10-23 14:59
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;


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
        userMapper.insert(new User(username, password, wtuId, userInfo.getName(), userInfo.getClassName()));
        return ServiceCode.SUCCESS;
    }
}
