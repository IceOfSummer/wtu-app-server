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


    /**
     * 更新用户信息
     * @param uid 用户id
     * @param user 要更新的信息(非null的属性都会被更新),<b>目前</b>只能更新{@link User#nickname} 其余字段无效
     * @return 服务码<p>
     *     - {@link ServiceCode#NOT_EXIST} 用户不存在
     */
    ServiceCode updateUserInfo(int uid, User user);


    /**
     * 申请邮箱绑定验证码
     * @param uid 用户id
     * @param email 邮箱
     * @return 服务码<p>
     *     - {@link ServiceCode#RATE_LIMIT} 达到了<b>每月</b>申请上限
     */
    ServiceCode requireEmailUpdateCaptcha(int uid, String email);

    /**
     * 更新邮箱
     * @param uid 用户id
     * @param captcha 邮箱验证码
     * @return 服务码<p>
     *     - {@link ServiceCode#BAD_REQUEST} 验证码不正确
     */
    ServiceCode updateEmail(int uid, String captcha);

}
