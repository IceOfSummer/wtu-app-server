package pers.xds.wtuapp.web.service.impl;

import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.service.CdnService;
import pers.xds.wtuapp.web.service.common.CdnManager;
import pers.xds.wtuapp.web.redis.CdnCache;

import java.io.IOException;

/**
 * @author DeSen Xu
 * @date 2022-09-11 17:04
 */
@Service
public class CdnServiceImpl implements CdnService {

    private CdnManager cdnManager;

    private CdnCache cdnCache;

    @Autowired
    public void setCdnManager(CdnManager cdnManager) {
        this.cdnManager = cdnManager;
    }

    @Autowired
    public void setCdnCache(CdnCache cdnCache) {
        this.cdnCache = cdnCache;
    }

    @Override
    public Response generateCdnAccessKey(int userId) throws IOException {
        int keyGenerateTimes = cdnCache.getKeyGenerateTimes(userId);
        final int maxAllowGenerate = 20;
        if (keyGenerateTimes > maxAllowGenerate) {
            return null;
        }
        return CosStsClient.getCredential(cdnManager.generateRequestTreeMap(userId));
    }

}
