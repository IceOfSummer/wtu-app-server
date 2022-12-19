package pers.xds.wtuapp.web.service;

import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.service.bean.BaseWtuUserInfo;

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
    User queryUserInfo(int userId);

    /**
     * 查询多个用户信息
     * @param ids 用户id
     * @return 多个用户信息, 若ids太长，则会返回null
     */
    @Nullable
    List<User> queryMultiUserInfo(List<Integer> ids);

    /**
     * 用户名是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean isUsernameExist(String username);

    /**
     * 教务系统学号是否已经存在
     * @param wtuUsername 学号
     * @return 是否存在
     */
    boolean isWtuUsernameExist(String wtuUsername);

    /**
     * 注册一个用户, 这里一定要先检查用户名和学号是否重复
     * @param username 用户名
     * @param password 密码。<b>此处的密码应该为单向加密后的密码</b>
     * @param wtuId 学号
     * @param userInfo 教务系统信息
     * @return 服务码<p>
     */
    ServiceCode registerUser(String username, String password, String wtuId, BaseWtuUserInfo userInfo);

    /**
     * 获取用户详细信息
     * @param uid 用户id
     * @return 用户详细信息，包括{@link User#email}, {@link User#nickname}, {@link User#wtuId}, {@link User#name}
     * {@link User#className}
     */
    User queryUserDetail(int uid);
}
