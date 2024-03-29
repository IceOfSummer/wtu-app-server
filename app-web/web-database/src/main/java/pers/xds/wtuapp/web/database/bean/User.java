package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.database.group.UpdateGroup;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * 用户表
 * @author HuPeng
 */
@TableName(value ="user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    @Null(groups = UpdateGroup.class)
    private Integer userId;

    /**
     * 用户名
     */
    @Null(groups = UpdateGroup.class)
    private String username;

    /**
     * 用户密码
     */
    @Null(groups = UpdateGroup.class)
    private String password;

    /**
     * 用户状态;0表示正常用户，其它数字根据需要设置，如1代表用户被封禁
     */
    @Null(groups = UpdateGroup.class)
    private Integer status;

    /**
     * 用户权限;使用位运算表示权限
     */
    @Null(groups = UpdateGroup.class)
    private String role;

    /**
     * 用户信誉
     */
    @Null(groups = UpdateGroup.class)
    private Integer credit;

    /**
     * 昵称
     */
    @Size(min = 1, max = 20, groups = UpdateGroup.class)
    private String nickname;

    /**
     * 用户邮箱
     */
    @Null(groups = UpdateGroup.class)
    private String email;

    /**
     * 学号
     */
    @Null(groups = UpdateGroup.class)
    private String wtuId;

    /**
     * 真实姓名
     */
    @Null(groups = UpdateGroup.class)
    private String name;

    /**
     * 班级信息
     */
    @Null(groups = UpdateGroup.class)
    private String className;


    public User() {
    }

    public User(String username, String password, String wtuId, String name, String className) {
        this.username = username;
        this.password = password;
        this.wtuId = wtuId;
        this.name = name;
        this.className = className;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
     * 用户权限;若有多个则以逗号分隔
     */
    public String getRole() {
        return role;
    }

    /**
     * 用户权限;若有多个则以逗号分隔
     */
    public void setRole(String role) {
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

    public String getWtuId() {
        return wtuId;
    }

    public void setWtuId(String wtuId) {
        this.wtuId = wtuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
