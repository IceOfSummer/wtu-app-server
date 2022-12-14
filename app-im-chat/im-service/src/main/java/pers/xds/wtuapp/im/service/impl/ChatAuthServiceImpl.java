package pers.xds.wtuapp.im.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.im.service.ChatAuthService;
import pers.xds.wtuapp.security.SecurityJwtProvider;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.security.bean.JwtParseResult;

/**
 * @author DeSen Xu
 * @date 2022-09-05 22:17
 */
@Service
public class ChatAuthServiceImpl implements ChatAuthService {


    private SecurityJwtProvider securityJwtProvider;

    @Autowired
    public void setSecurityJwtProvider(SecurityJwtProvider securityJwtProvider) {
        this.securityJwtProvider = securityJwtProvider;
    }

    @Override
    public UserPrincipal findUser(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return null;
        }
        JwtParseResult jwtParseResult = securityJwtProvider.parseJwt(jwt);
        if (jwtParseResult == null || jwtParseResult.isExpired()) {
            return null;
        }
        return jwtParseResult.getPrincipal();
    }

}
