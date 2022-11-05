package pers.xds.wtuapp.web.service;

import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.service.bean.UserInfo;

import java.util.List;

/**
 * @author HuPeng
 */
public interface UserService {

    /**
     * 查询用户信息
     * @param userId 用户id
     * @return 用户教务系统信息
     */
    @Nullable
    UserInfo queryUserInfo(int userId);

    /**
     * 查询多个用户信息
     * @param ids 用户id
     * @return 多个用户信息, 若ids太长或数组长度为0，则会返回null
     */
    @Nullable
    List<UserInfo> queryMultiUserInfo(List<Integer> ids);

}
