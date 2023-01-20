package pers.xds.wtuapp.web.security.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author DeSen Xu
 * @date 2022-12-31 21:32
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthSuccessResponse {

    /**
     * jwt
     */
    public String token;

    public int userId;

    public String email;

    public String nickname;

    public String wtuId;

    public String name;

    public String className;

    public int roles;

    public AuthSuccessResponse(String token, int userId, String email, String nickname, String wtuId, String name, String className, int roles) {
        this.userId = userId;
        this.token = token;
        this.email = email;
        this.nickname = nickname;
        this.wtuId = wtuId;
        this.name = name;
        this.className = className;
        this.roles = roles;
    }

}
