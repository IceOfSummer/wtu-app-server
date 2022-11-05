package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.xds.wtuapp.web.database.bean.Commodity;

/**
 * @author DeSen Xu
 * @date 2022-09-09 11:27
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {

    String COLUMN_COMMODITY_ID = "commodity_id";

    String COLUMN_OWNER_ID = "owner_id";

    String COLUMN_NAME = "name";

    String COLUMN_CREATE_TIME = "create_time";

    String COLUMN_PRICE = "price";

    String COLUMN_PREVIEW_IMAGE = "preview_image";

    String COLUMN_TRADE_LOCATION = "trade_location";

    String COLUMN_DESCRIPTION = "description";

    String COLUMN_IMAGES = "images";

    String TRADE_LOCATION = "trade_location";



}
