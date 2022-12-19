package pers.xds.wtuapp.web.service.config.email;

/**
 * @author DeSen Xu
 * @date 2022-12-19 15:27
 */
public class EmailBindTemplateData {

    /**
     * 用户昵称(不是用户名)
     */
    public String username;

    /**
     * 验证码
     */
    public Object captcha;

    /**
     * 当前操作名称，如'邮箱绑定'
     */
    public String action;

    public EmailBindTemplateData(String username, String captcha, String action) {
        this.username = username;
        this.captcha = captcha;
        this.action = action;
    }

}
