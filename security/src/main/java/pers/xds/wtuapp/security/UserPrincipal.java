package pers.xds.wtuapp.security;

import org.springframework.security.core.userdetails.User;

/**
 * @author DeSen Xu
 * @date 2022-09-01 14:14
 */
public final class UserPrincipal extends User {

    public static final long serialVersionUID = 1;

    private final int id;

    public UserPrincipal(String username, String password, Integer role, int uid) {
        super(username, password, Roles.parseUserRoles(role));
        this.id = uid;
    }

    public int getId() {
        return id;
    }

}
