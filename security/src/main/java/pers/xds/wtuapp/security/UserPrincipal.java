package pers.xds.wtuapp.security;

import org.springframework.security.core.userdetails.User;

/**
 * 用户凭据。
 * <p>
 * <b>{@link UserPrincipal#getUsername()}的值永远为用户id</b>
 * @author DeSen Xu
 * @date 2022-09-01 14:14
 */
public final class UserPrincipal extends User {

    private final int id;


    /**
     * 用于从jwt中生成用户凭据
     */
    public UserPrincipal(int uid, int compressedRole) {
        super(String.valueOf(uid), "", Roles.parseFromCompressedInteger(compressedRole));
        this.id = uid;
    }

    public int getId() {
        return id;
    }


}
