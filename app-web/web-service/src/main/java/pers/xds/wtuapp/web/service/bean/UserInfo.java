package pers.xds.wtuapp.web.service.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.database.bean.WtuUserInfo;

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
    private Integer uid;

    /**
     * 用户<b>真实</b>姓名
     */
    @JsonProperty("n")
    private String name;

    /**
     * 用户寝室位置
     */
    @JsonProperty("b")
    private String bedroom;

    /**
     * 用户信誉(0-100)
     */
    @JsonProperty("c")
    private Integer credit;

    /**
     * 昵称
     */
    @JsonProperty("i")
    private String nickname;

    public UserInfo(User user, WtuUserInfo wtuUserInfo) {
        this.uid = user.getUserId();
        this.credit = user.getCredit();
        if (wtuUserInfo != null) {
            this.name = wtuUserInfo.getName();
            this.bedroom = wtuUserInfo.getBedroom();
        }
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
}
