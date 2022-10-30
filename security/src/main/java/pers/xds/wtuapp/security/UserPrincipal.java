package pers.xds.wtuapp.security;

import org.springframework.security.core.userdetails.User;

/**
 * @author DeSen Xu
 * @date 2022-09-01 14:14
 */
public final class UserPrincipal extends User {

    public static final long serialVersionUID = 1;

    private final int id;

    private final String wtuUsername;

    public UserPrincipal(String username, String password, Integer role, int uid, String wtuUsername) {
        super(username, password, Roles.parseUserRoles(role));
        this.id = uid;
        this.wtuUsername = wtuUsername;
    }

    public int getId() {
        return id;
    }

    public String getWtuUsername() {
        return wtuUsername;
    }

}
