package pers.xds.wtuapp.web.service.bean;

import pers.xds.wtuapp.web.service.config.cos.SignInfo;

/**
 * @author DeSen Xu
 * @date 2022-11-23 22:45
 */
public class SignWrapper {

    public String token;

    public SignInfo[] signs;

    public SignWrapper(String token, SignInfo[] signs) {
        this.token = token;
        this.signs = signs;
    }


}
