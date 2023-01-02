package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.database.bean.UserAuth;

/**
 * 用户认证服务，主要管理jwt相关内容
 * @author DeSen Xu
 * @date 2023-01-01 21:41
 */
public interface UserAuthService {

    /**
     * 获取并且将jwtId的字段加一
     * @param uid 用户id
     * @param version 乐观锁版本号{@link UserAuth#version}
     */
    void increaseJwtId(int uid, int version);

    /**
     * 获取最新的jwtId
     * @param uid 用户id
     * @return 包含最新的jwtId的用户认证信息
     */
    UserAuth queryNewestJwtId(int uid);

}
