package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.xds.wtuapp.web.database.bean.WtuUserInfo;

/**
* @author HuPeng
* @date  2022-10-23 15:01:34
*/
@Mapper
public interface WtuUserInfoMapper extends BaseMapper<WtuUserInfo> {

    String COLUMN_WTU_USERNAME = "wtu_username";

    String COLUMN_USER_ID = "user_id";

}
