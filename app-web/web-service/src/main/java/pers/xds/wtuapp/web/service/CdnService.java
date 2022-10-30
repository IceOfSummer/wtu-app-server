package pers.xds.wtuapp.web.service;

import com.tencent.cloud.Response;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * @author DeSen Xu
 * @date 2022-09-11 17:03
 */
public interface CdnService {


    /**
     * 生成CDN临时访问密匙
     * @param userId 用户id, 每个人每天生成次数有限制
     * @return 临时CDN密匙，若达到了生成限制，返回null
     * @throws IOException 获取失败
     */
    @Nullable
    Response generateCdnAccessKey(int userId) throws IOException;
}
