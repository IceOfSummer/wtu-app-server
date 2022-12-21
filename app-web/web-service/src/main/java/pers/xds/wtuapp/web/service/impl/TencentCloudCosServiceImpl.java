package pers.xds.wtuapp.web.service.impl;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.web.redis.CounterCache;
import pers.xds.wtuapp.web.redis.common.Duration;
import pers.xds.wtuapp.web.service.CosService;
import pers.xds.wtuapp.web.service.config.cos.CosProvider;
import pers.xds.wtuapp.web.service.config.cos.SignInfo;

/**
 * 腾讯云对象存储服务实现
 * @author DeSen Xu
 * @date 2022-11-20 16:36
 */
@Service
public class TencentCloudCosServiceImpl implements CosService {

    private CounterCache counterCache;

    @Autowired
    public void setCounterCache(CounterCache counterCache) {
        this.counterCache = counterCache;
    }

    private CosProvider cosProvider;

    @Autowired
    public void setCosProvider(CosProvider cosProvider) {
        this.cosProvider = cosProvider;
    }


    private static final String USERSPACE_KEY_PREFIX = "CosService:Userspace:";

    @Override
    public SignInfo[] requireUserspaceUploadSign(int uid, String[] filenames)  {
        final int maxAllowTimes = 10;
        String key = USERSPACE_KEY_PREFIX + uid;
        int keyGenerateTimes = counterCache.getInvokeCount(key, Duration.DAY);
        if (keyGenerateTimes > maxAllowTimes) {
            return null;
        }
        try {
            SignInfo[] signInfos = cosProvider.signUserspaceUpload(uid, filenames);
            counterCache.increaseInvokeCount(key);
            return signInfos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String AVATAR_PREFIX = "CosService:Avatar:";

    @Nullable
    @Override
    public SignInfo requireAvatarUploadSign(int uid, String type) {
        final int maxAllowTimes = 3;
        String key = AVATAR_PREFIX + uid;
        int invokeCount = counterCache.getInvokeCount(key, Duration.MONTH);
        if (invokeCount >= maxAllowTimes) {
            return null;
        }
        try {
            return cosProvider.signAvatarUpload(uid, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
