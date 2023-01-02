package pers.xds.wtuapp.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.database.bean.UserAuth;
import pers.xds.wtuapp.web.database.mapper.UserAuthMapper;
import pers.xds.wtuapp.web.service.UserAuthService;

/**
 * @author DeSen Xu
 * @date 2023-01-01 21:44
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private UserAuthMapper userAuthMapper;

    @Autowired
    public void setUserAuthMapper(UserAuthMapper userAuthMapper) {
        this.userAuthMapper = userAuthMapper;
    }

    @Override
    public void increaseJwtId(int uid, int version) {
        userAuthMapper.increaseNewestId(uid, version);
    }

    @Override
    public UserAuth queryNewestJwtId(int uid) {
        UserAuth userAuth = userAuthMapper.selectBytId(uid);
        if (userAuth == null) {
            userAuth = new UserAuth(uid);
            userAuthMapper.insert(uid);
        }
        return userAuth;
    }

}
