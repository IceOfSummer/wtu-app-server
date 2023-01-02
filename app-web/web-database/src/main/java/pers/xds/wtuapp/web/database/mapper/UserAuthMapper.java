package pers.xds.wtuapp.web.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jetbrains.annotations.Nullable;
import pers.xds.wtuapp.web.database.bean.UserAuth;

/**
 * @author DeSen Xu
 * @date 2023-01-01 21:23
 */
@Mapper
public interface UserAuthMapper {

    /**
     * 获取用户身份令牌的最新信息
     * @param uid 用户id
     * @return 最新信息, 可能为null(返回null时，最新jwtId应该视为0)。<b>不会获取{@link UserAuth#uid}字段</b>
     */
    @Nullable
    UserAuth selectBytId(@Param("uid") int uid);

    /**
     * 让最新令牌id加一
     * @param uid 用户id
     * @param version 乐观锁
     * @return 返回1表示成功，0表示乐观锁冲突
     */
    int increaseNewestId(@Param("uid") int uid, @Param("v") int version);

    /**
     * 插入新的用户认证信息
     * @param uid 用户id
     */
    void insert(@Param("uid") int uid);

}
