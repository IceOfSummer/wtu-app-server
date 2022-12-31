package pers.xds.wtuapp.security;

import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-01 14:14
 */
public final class UserPrincipal extends User {

    private final int id;

    public UserPrincipal(String username, String password, Integer role, int uid) {
        super(username, password, Roles.parseUserRoles(role));
        this.id = uid;
    }

    public UserPrincipal(int uid, String username, List<String> roles) {
        super(username, "", Roles.fromStringList(roles));
        this.id = uid;
    }

    public int getId() {
        return id;
    }

}
