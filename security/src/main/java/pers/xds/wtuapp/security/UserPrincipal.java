package pers.xds.wtuapp.security;

import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * 用户凭据。
 * <p>
 * <b>{@link UserPrincipal#getUsername()}的值永远为用户id</b>
 * @author DeSen Xu
 * @date 2022-09-01 14:14
 */
public final class UserPrincipal extends User {

    private final int id;

    public UserPrincipal(String password, Integer role, int uid) {
        super(String.valueOf(uid), password, Roles.parseUserRoles(role));
        this.id = uid;
    }

    public UserPrincipal(int uid, List<String> roles) {
        super(String.valueOf(uid), "", Roles.fromStringList(roles));
        this.id = uid;
    }

    public int getId() {
        return id;
    }

}
