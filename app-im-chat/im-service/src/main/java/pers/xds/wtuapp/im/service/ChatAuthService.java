package pers.xds.wtuapp.im.service;


import org.springframework.lang.Nullable;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * @author DeSen Xu
 * @date 2022-09-02 18:52
 */
public interface ChatAuthService {

    /**
     * 获取凭据
     * @param jwt jwt信息
     * @return 用户信息
     */
    @Nullable
    UserPrincipal findUser(String jwt);

}
