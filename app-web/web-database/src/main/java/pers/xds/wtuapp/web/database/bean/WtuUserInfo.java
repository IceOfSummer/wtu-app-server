package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 账号教务系统信息
 * @author HuPeng
 */
@TableName(value ="wtu_user_info")
public class WtuUserInfo implements Serializable {
    /**
     * 用户id
     */
    @TableId
    private Integer userId;

    /**
     * 学号
     */
    private String wtuUsername;


    /**
     * 姓名
     */
    private String name;

    /**
     * 
     */
    private String bedroom;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    public String getWtuUsername() {
        return wtuUsername;
    }

    /**
     * 学号
     */
    public void setWtuUsername(String wtuUsername) {
        this.wtuUsername = wtuUsername;
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

}