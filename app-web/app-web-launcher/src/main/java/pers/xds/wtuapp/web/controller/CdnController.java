package pers.xds.wtuapp.web.controller;

import com.tencent.cloud.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.service.CdnService;
import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import java.io.IOException;

/**
 * @author DeSen Xu
 * @date 2022-09-11 17:02
 */
@RestController
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
@RequestMapping("cdn")
public class CdnController {

    private CdnService cdnService;

    @Autowired
    public void setCdnService(CdnService cdnService) {
        this.cdnService = cdnService;
    }

    @GetMapping("key")
    public ResponseEntity<ResponseTemplate<Response>> getCdnAccessKey() throws IOException {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        Response response = cdnService.generateCdnAccessKey(userPrincipal.getId());
        if (response == null) {
            return ResponseTemplate.fail(ResponseCode.RATE_LIMIT, (Response) null).toResponseEntity();
        }
        return ResponseTemplate.success(response).toResponseEntity();
    }

}
