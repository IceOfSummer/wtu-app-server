package pers.xds.wtuapp.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.redis.CosCache;
import pers.xds.wtuapp.web.service.CosService;
import pers.xds.wtuapp.web.service.config.cos.CosProvider;
import pers.xds.wtuapp.web.service.config.cos.SignInfo;
import pers.xds.wtuapp.web.service.config.cos.tencent.TencentCloudCosProviderImpl;

/**
 * 腾讯云对象存储服务实现
 * @author DeSen Xu
 * @date 2022-11-20 16:36
 */
@Service
public class TencentCloudCosServiceImpl implements CosService {

    private static final Logger log = LoggerFactory.getLogger(TencentCloudCosProviderImpl.class);

    private CosCache cosCache;

    private CosProvider cosProvider;

    @Autowired
    public void setCosProvider(CosProvider cosProvider) {
        this.cosProvider = cosProvider;
    }

    @Autowired
    public void setCosCache(CosCache cosCache) {
        this.cosCache = cosCache;
    }


    @Override
    public SignInfo[] requireUserspaceUploadSign(int uid, String[] filenames)  {
        final int maxAllowTimes = 20;
        int keyGenerateTimes = cosCache.getKeyGenerateTimes(uid);
        if (keyGenerateTimes > maxAllowTimes) {
            return null;
        }
        try {
            return cosProvider.signUserspaceUpload(uid, filenames);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }
}
