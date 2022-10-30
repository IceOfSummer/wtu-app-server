package pers.xds.wtuapp.im.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.xds.wtuapp.im.database.bean.User;

/**
 * @author HuPeng
 * @date 2022-10-30 15:13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
