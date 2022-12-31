package pers.xds.wtuapp.web.security.common;

import pers.xds.wtuapp.security.Roles;
import pers.xds.wtuapp.web.database.bean.User;

/**
 * 仅用于初始化登录时使用的凭据
 * @author DeSen Xu
 * @date 2022-12-31 21:45
 */
public class UserLoginPrincipal extends org.springframework.security.core.userdetails.User {

    private final User user;

    public UserLoginPrincipal(User user) {
        super(user.getUsername(), user.getPassword(), Roles.parseUserRoles(user.getRole()));
        this.user = user;
    }

    public Integer getUid() {
        return user.getUserId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getNickname() {
        return user.getNickname();
    }

    public String getWtuId() {
        return user.getWtuId();
    }

    public String getName() {
        return user.getName();
    }

    public String getClassName() {
        return user.getClassName();
    }


}
