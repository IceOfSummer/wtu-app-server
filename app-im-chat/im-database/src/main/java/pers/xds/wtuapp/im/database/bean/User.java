package pers.xds.wtuapp.im.database.bean;

import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @author HuPeng
 * @date 2022-10-30 15:14
 */
public class User {

    @TableId
    private Integer userId;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
