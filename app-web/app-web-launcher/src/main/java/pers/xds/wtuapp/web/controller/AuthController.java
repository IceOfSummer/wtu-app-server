package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.xds.wtuapp.security.SecurityJwtProvider;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.security.bean.JwtParseResult;
import pers.xds.wtuapp.security.token.ExpiredAuthenticationToken;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.database.bean.UserAuth;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.UserAuthService;

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
    public ResponseTemplate<String> renewRequestToken() {
        Authentication authentication = SecurityContextUtil.getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            return ResponseTemplate.fail(ResponseCode.ACCESS_DENIED);
        }
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        UserAuth userAuth = userAuthService.queryNewestJwtId(principal.getId());

        int newestId = userAuth.getJwtId();
        if (authentication instanceof ExpiredAuthenticationToken) {
            // 这里使用旧Token换新Token
            if (isInvalidToken((ExpiredAuthenticationToken) authentication, newestId)) {
                return ResponseTemplate.fail(ResponseCode.ACCESS_DENIED);
            }
        } else if (!authentication.isAuthenticated()) {
            // 这里的预期应该是登录了的用户重新换最新的Token，所以应该是已经认证了的
            return ResponseTemplate.fail(ResponseCode.ACCESS_DENIED);
        }
        userAuthService.increaseJwtId(principal.getId(), userAuth.getVersion());
        String jwt = securityJwtProvider.generateJwt(principal, newestId + 1);
        return ResponseTemplate.success(jwt);
    }

    private boolean isInvalidToken(ExpiredAuthenticationToken token, int newestId) {
        JwtParseResult jwtParseResult = token.getJwtParseResult();
        int jwtId = jwtParseResult.getJwtId();
        return jwtId != newestId;
    }

}
