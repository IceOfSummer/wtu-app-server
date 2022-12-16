package pers.xds.wtuapp.web.service.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户简要信息
 * @author HuPeng
 * @date 2022-10-23 14:54
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {

    /**
     * 用户uid
     */
    @JsonProperty("u")
    public Integer uid;


    /**
     * 用户<b>真实</b>姓名
     */
    @JsonProperty("n")
    public String name;

    /**
     * 用户信誉(0-100)
     */
    @JsonProperty("c")
    public Integer credit;

    /**
     * 昵称
     */
    @JsonProperty("i")
    public String nickname;

    /**
     * email
     */
    @JsonProperty("e")
    public String email;

    public UserInfo() {
    }

    public UserInfo(Integer uid, String name, Integer credit, String nickname, String email) {
        this.uid = uid;
        this.name = name;
        this.credit = credit;
        this.nickname = nickname;
        this.email = email;
    }
}
