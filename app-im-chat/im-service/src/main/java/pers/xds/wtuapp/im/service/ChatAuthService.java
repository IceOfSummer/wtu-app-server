package pers.xds.wtuapp.im.service;


import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author DeSen Xu
 * @date 2022-09-02 18:52
 */
public interface ChatAuthService {

    /**
     * 获取凭据
     * @param session session信息
     * @return 用户信息
     */
    @Nullable
    UsernamePasswordAuthenticationToken findUser(String session);

}
