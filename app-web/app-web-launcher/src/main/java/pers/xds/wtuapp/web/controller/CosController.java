package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.aop.RateLimit;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.CosService;
import pers.xds.wtuapp.web.service.config.cos.SignInfo;

import java.util.UUID;

/**
 * 腾讯云对象存储接口
 * <p>
 * 这里直接用服务器端签名，用户只能上传指定的文件名到指定的文件夹中
 * @author DeSen Xu
 * @date 2022-09-11 17:02
 */
@RestController
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
@RequestMapping("cos")
public class CosController {

    private CosService cosService;

    @Autowired
    public void setCosService(CosService cosService) {
        this.cosService = cosService;
    }

    /**
     * 用户空间上传限制
     */
    private static final int USER_SPACE_UPLOAD_LIMIT = 20;

    /**
     * 生成用户空间对象存储桶访问密匙
     * @param count 要上传的文件数量
     * @param type 文件的类型描述符，如`.png`
     * @return 对象存储桶访问密匙
     */
    @GetMapping("/secret/userspace")
    @RateLimit(value = USER_SPACE_UPLOAD_LIMIT, limitMessage = "您每天最多上传" + USER_SPACE_UPLOAD_LIMIT + "次")
    public ResponseTemplate<SignInfo[]> getUserSpaceUploadSecret(int count,
                                                              @RequestParam(required = false, defaultValue = "") String type) {
        final int maxTypeLength = 5;
        final int maxAllUploadCount = 5;
        if (type.length() > maxTypeLength || count >= maxAllUploadCount) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        String[] filenames = new String[count];
        for (int i = 0; i < count; i++) {
            filenames[i] = UUID.randomUUID() + type;
        }
        SignInfo[] signInfos = cosService.requireUserspaceUploadSign(userPrincipal.getId(), filenames);
        if (signInfos == null) {
            return ResponseTemplate.fail(ResponseCode.RATE_LIMIT);
        }
        return ResponseTemplate.success(signInfos);
    }

    /**
     * 头像上传限制
     */
    private static final int AVATAR_UPLOAD_LIMIT = 3;

    /**
     * 获取头像上传签名
     * @param type 文件的类型描述符，如`.png`
     */
    @GetMapping("/secret/avatar")
    @RateLimit(value = AVATAR_UPLOAD_LIMIT, limitMessage = "您每天最多更换" + AVATAR_UPLOAD_LIMIT + "次头像")
    public ResponseTemplate<SignInfo> getAvatarUploadSign(@RequestParam("t") String type) {
        if (type.length() > 6 || type.charAt(0) != '.') {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        SignInfo sign = cosService.requireAvatarUploadSign(userPrincipal.getId(), type);
        if (sign == null) {
            return ResponseTemplate.fail(ResponseCode.RATE_LIMIT);
        }
        return ResponseTemplate.success(sign);
    }

    private static final int PUBLIC_SPACE_UPLOAD_LIMIT = 20;

    /**
     * 获取公共空间上传签名
     * @param type  文件的类型描述符，如`.png`
     */
    @GetMapping("/secret/public")
    @RateLimit(value = PUBLIC_SPACE_UPLOAD_LIMIT, limitMessage = "您每天最多上传" + PUBLIC_SPACE_UPLOAD_LIMIT + "图片")
    public ResponseTemplate<SignInfo> getPublicSpaceSign(@RequestParam("t") String type) {
        if (type.length() > 6 || type.charAt(0) != '.') {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        SignInfo signInfo = cosService.requirePublicSpaceSign(userPrincipal.getId(), UUID.randomUUID() + type);
        if (signInfo == null) {
            return ResponseTemplate.fail(ResponseCode.RATE_LIMIT);
        }
        return ResponseTemplate.success(signInfo);
    }

}
