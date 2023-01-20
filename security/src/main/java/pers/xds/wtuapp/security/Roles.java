package pers.xds.wtuapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-08-31 15:43
 */
public class Roles {

    /**
     * 普通用户
     */
    public static final String ROLE_NORMAL_USER = "ROLE_" + SecurityConstant.ROLE_NORMAL_USER;

    /**
     * 管理员
     */
    public static final String ROLE_ADMIN = "ROLE_" + SecurityConstant.ROLE_ADMIN;

    private static final String[] ROLES_MAPPING = new String[]{ ROLE_NORMAL_USER, ROLE_ADMIN };


    private Roles() {}


    /**
     * 压缩用户权限，将结果以一个整数表示
     */
    public static int compressRoleList(Collection<GrantedAuthority> roles) {
        int result = 0;
        for (GrantedAuthority role : roles) {
            String authority = role.getAuthority();
            if (ROLE_NORMAL_USER.equals(authority)) {
                result |= 0b1;
            } else if (ROLE_ADMIN.equals(authority)) {
                result |= 0b10;
            }
        }
        return result;
    }

    /**
     * 从压缩的数字中解析权限
     */
    public static List<GrantedAuthority> parseFromCompressedInteger(int value) {
        ArrayList<GrantedAuthority> result = new ArrayList<>(2);
        for (int i = 0; i < ROLES_MAPPING.length; i++) {
            if ((value >> i & 1) == 1) {
                result.add(new SimpleGrantedAuthority(ROLES_MAPPING[i]));
            }
        }
        return result;
    }

    public static List<GrantedAuthority> formRoleString(String role) {
        String[] roles = role.split(",");
        ArrayList<GrantedAuthority> result = new ArrayList<>(roles.length);
        for (String s : roles) {
            result.add(new SimpleGrantedAuthority(s));
        }
        return result;
    }


}
