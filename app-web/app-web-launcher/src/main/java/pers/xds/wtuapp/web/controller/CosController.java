package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.CosService;
import pers.xds.wtuapp.web.service.bean.SignWrapper;

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
     * 生成用户空间对象存储桶访问密匙
     * @param count 要上传的文件数量
     * @param type 文件的类型描述符，如`.png`
     * @return 对象存储桶访问密匙
     */
    @GetMapping("/secret/userspace")
    public ResponseTemplate<SignWrapper> getUserSpaceUploadSecret(int count,
                                                              @RequestParam(required = false, defaultValue = "") String type) {
        final int maxAllUploadCount = 5;
        if (count >= maxAllUploadCount) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        String[] filenames = new String[count];
        for (int i = 0; i < count; i++) {
            filenames[i] = UUID.randomUUID() + type;
        }
        SignWrapper response = cosService.requireUserspaceUploadSign(userPrincipal.getId(), filenames);
        if (response == null) {
            return ResponseTemplate.fail(ResponseCode.RATE_LIMIT);
        }
        return ResponseTemplate.success(response);
    }

}
