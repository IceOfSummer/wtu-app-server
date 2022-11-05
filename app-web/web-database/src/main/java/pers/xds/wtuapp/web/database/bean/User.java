package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * 用户表
 * @author HuPeng
 */
@TableName(value ="user")
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态;0表示正常用户，其它数字根据需要设置，如1代表用户被封禁
     */
    private Integer status;

    /**
     * 用户权限;使用位运算表示权限
     */
    private Integer role;

    /**
     * 用户信誉
     */
    private Integer credit;

    /**
     * 昵称
     */
    private String nickname;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public User() {
    }

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

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
     * 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
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
     * 用户权限;使用位运算表示权限
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 用户权限;使用位运算表示权限
     */
    public void setRole(Integer role) {
        this.role = role;
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

    @Nullable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}