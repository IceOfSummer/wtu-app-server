package pers.xds.wtuapp.web.database.bean;

/**
 * @author DeSen Xu
 * @date 2023-01-01 21:23
 */
public class UserAuth {

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 最新的jwtId
     */
    private Integer jwtId;

    /**
     * 乐观锁
     */
    private Integer version;

    public UserAuth() {
    }

    public UserAuth(Integer uid) {
        this.uid = uid;
        this.jwtId = 0;
        this.version = 0;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getJwtId() {
        return jwtId;
    }

    public void setJwtId(Integer jwtId) {
        this.jwtId = jwtId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
