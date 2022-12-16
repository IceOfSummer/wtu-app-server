package pers.xds.wtuapp.web.service.bean;

/**
 * @author DeSen Xu
 * @date 2022-12-16 17:39
 */
public class BaseWtuUserInfo {

    private final String name;

    private final String className;

    public BaseWtuUserInfo(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }
}
