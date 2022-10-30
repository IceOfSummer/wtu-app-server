package pers.xds.wtuapp.web.database.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @author DeSen Xu
 * @date 2022-09-16 21:57
 */
@TableName(value ="user_info_view")
public class UserInfoView implements Serializable {

    /**
     * 用户id
     */
    @TableId
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 教务系统用户名
     */
    private String wtuUsername;

    /**
     * 用户状态;0表示正常用户，其它数字根据需要设置，如1代表用户被封禁
     */
    private Integer status;

    /**
     * 用户信誉
     */
    private Integer credit;

    /**
     * 姓名
     */
    private String name;

    /**
     * 寝室位置
     */
    private String bedroom;

    /**
     * 用户头像
     */
    @Nullable
    private String avatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 教务系统用户名
     */
    public String getWtuUsername() {
        return wtuUsername;
    }

    /**
     * 教务系统用户名
     */
    public void setWtuUsername(String wtuUsername) {
        this.wtuUsername = wtuUsername;
    }

    /**
     * 用户状态;0表示正常用户，其它数字根据需要设置，如1代表用户被封禁
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 用户状态;0表示正常用户，其它数字根据需要设置，如1代表用户被封禁
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 用户信誉
     */
    public Integer getCredit() {
        return credit;
    }

    /**
     * 用户信誉
     */
    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    /**
     * 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public String getBedroom() {
        return bedroom;
    }

    /**
     * 
     */
    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", userId=" + userId +
                ", username=" + username +
                ", wtuUsername=" + wtuUsername +
                ", status=" + status +
                ", credit=" + credit +
                ", name=" + name +
                ", bedroom=" + bedroom +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}