package pers.xds.wtuapp.web.bean;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 在类下的getter方法是给spring mvc绑定数据用的
 * @author DeSen Xu
 * @date 2022-12-16 16:25
 */
public class RegisterParam {

    /**
     * ru
     */
    @NotEmpty
    @Size(min = 8, max = 25)
    public String registerUsername;

    /**
     * rp
     */
    @NotEmpty
    @Size(min = 8, max = 25)
    public String registerPassword;

    /**
     * un
     */
    @NotEmpty
    public String wtuUsername;

    /**
     * wp
     */
    @NotEmpty
    public String wtuPassword;

    /**
     * wc
     */
    @NotEmpty
    public String wtuCaptcha;

    /**
     * lt
     */
    @NotEmpty
    public String lt;

    /**
     * JSESSION_ID
     */
    @NotEmpty
    public String sid;

    @NotEmpty
    public String route;

    public void setru(String registerUsername) {
        this.registerUsername = registerUsername;
    }

    public void setrp(String registerPassword) {
        this.registerPassword = registerPassword;
    }

    public void setun(String wtuUsername) {
        this.wtuUsername = wtuUsername;
    }

    public void setwp(String wtuPassword) {
        this.wtuPassword = wtuPassword;
    }

    public void setwc(String wtuCaptcha) {
        this.wtuCaptcha = wtuCaptcha;
    }

    public void setlt(String lt) {
        this.lt = lt;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}
