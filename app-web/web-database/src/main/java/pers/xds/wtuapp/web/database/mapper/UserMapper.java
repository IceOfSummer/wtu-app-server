package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import pers.xds.wtuapp.web.database.bean.User;

/**
 * @author DeSen Xu
 * @date 2022-08-31 17:10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    String COLUMN_USER_ID = "user_id";

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 指定用户
     */
    @Nullable
    User findByUsername(@Param("username") String username);

}
