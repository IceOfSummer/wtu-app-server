package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.SecurityJwtProvider;
import pers.xds.wtuapp.security.bean.JwtParseResult;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.bean.UserAuth;
import pers.xds.wtuapp.web.service.UserAuthService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author DeSen Xu
 * @date 2023-01-03 15:03
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    private SecurityJwtProvider securityJwtProvider;

    private UserAuthService userAuthService;

    @Autowired
    public void setUserAuthService(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Autowired
    public void setSecurityJwtProvider(SecurityJwtProvider securityJwtProvider) {
        this.securityJwtProvider = securityJwtProvider;
    }

    /**
     * 续签请求token
     * @return 请求token
     */
    @GetMapping("renew")
    public ResponseTemplate<String> renewRequestToken(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.HEADER_AUTHORIZATION);
        if (token == null) {
            return ResponseTemplate.fail(ResponseCode.ACCESS_DENIED);
        }
        JwtParseResult jwtParseResult = securityJwtProvider.parseJwt(token);
        if (jwtParseResult == null) {
            return ResponseTemplate.fail(ResponseCode.ACCESS_DENIED);
        }
        int uid = jwtParseResult.getPrincipal().getId();
        UserAuth userAuth = userAuthService.queryNewestJwtId(uid);
        if (userAuth.getJwtId() != jwtParseResult.getJwtId()) {
            return ResponseTemplate.fail(ResponseCode.ACCESS_DENIED);
        }
        userAuthService.increaseJwtId(uid, userAuth.getVersion());
        String jwt = securityJwtProvider.generateJwt(jwtParseResult.getPrincipal(), jwtParseResult.getJwtId() + 1);
        return ResponseTemplate.success(jwt);
    }

}
