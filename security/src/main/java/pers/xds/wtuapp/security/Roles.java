package pers.xds.wtuapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-08-31 15:43
 */
public enum Roles {
    /**
     * 普通用户
     */
    NORMAL_USER(new SimpleGrantedAuthority(SecurityConstant.ROLE_NORMAL_USER));

    public final GrantedAuthority grantedAuthority;

    Roles(GrantedAuthority grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    public static List<GrantedAuthority> parseUserRoles(Integer role) {
        if (role == null) {
            return Collections.emptyList();
        }
        int index = 0;
        ArrayList<GrantedAuthority> list = new ArrayList<>(2);
        while (role > 0) {
            if ((role >> index & 1) == 1) {
                list.add(Roles.values()[index].grantedAuthority);
            }
            role >>= 1;
            index++;
        }
        return list;
    }

    public static GrantedAuthority fromString(String role) {
        if (role.equals(SecurityConstant.ROLE_NORMAL_USER)) {
            return NORMAL_USER.grantedAuthority;
        }
        return null;
    }

    public static List<GrantedAuthority> fromStringList(List<String> roles) {
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles.size());
        for (String role : roles) {
            grantedAuthorities.add(fromString(role));
        }
        return grantedAuthorities;
    }
}
