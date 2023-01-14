package pers.xds.wtuapp.web.service.impl;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    private CosProvider cosProvider;

    @Autowired
    public void setCosProvider(CosProvider cosProvider) {
        this.cosProvider = cosProvider;
    }


    @Override
    public SignInfo[] requireUserspaceUploadSign(int uid, String[] filenames)  {
        try {
            return cosProvider.signUserspaceUpload(uid, filenames);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public SignInfo requireAvatarUploadSign(int uid, String type) {
        try {
            return cosProvider.signAvatarUpload(uid, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Nullable
    @Override
    public SignInfo requirePublicSpaceSign(int uid, String filename) {
        try {
            return cosProvider.signPublicSpaceUpload(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
