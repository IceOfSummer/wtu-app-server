package pers.xds.wtuapp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.database.mapper.UserMapper;
import pers.xds.wtuapp.web.service.CommodityService;

/**
 * 用于初始化本地数据库
 * @author DeSen Xu
 * @date 2022-11-03 17:25
 */
@Component
@SpringBootTest
public class TestServerInitializer {


    public static final String TEST_PASSWORD = "abc123";

    private UserMapper userMapper;

    private CommodityService commodityService;

    @Autowired
    public void setCommodityService(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void doInit() {
        String password = new BCryptPasswordEncoder().encode(TEST_PASSWORD);
        // insert test user
        for (int i = 0; i < 5; i++) {
            User user = new User("10000" + i, password, "TestUser-" + i);
            userMapper.insert(user);
        }

        // insert commodity
        Commodity commodity = new Commodity(2, "苹果14 手机 iPhone14", "爱疯14", 9999D, "5B212", "http://cdn.xds.fit/image/1/39282A5B-19A6-F2C8-28E7-B62B047D1D70.png", "[\"http://cdn.xds.fit/image/1/39282A5B-19A6-F2C8-28E7-B62B047D1D70.png\"]");
        commodity.setPrice(Math.random() * 1000);
        commodityService.insertCommodity(commodity);

        commodity.setName("新鲜苹果 Apple");
        commodity.setTradeLocation("11栋自取");
        commodity.setDescription("爱疯10");
        commodity.setPrice(Math.random() * 1000);
        commodityService.insertCommodity(commodity);

        commodity.setTradeLocation("5栋自取");
        commodity.setName( "新鲜苹果核 FreshApple");
        commodity.setDescription("爱疯10");
        commodity.setPrice(Math.random() * 1000);
        commodityService.insertCommodity(commodity);

        commodity.setTradeLocation("送货上门");
        commodity.setName("坏苹果 BadApple");
        commodity.setPrice(Math.random() * 1000);
        commodity.setDescription("爱疯10");
        commodityService.insertCommodity(commodity);

        commodity.setName("苹果手机15 iPhone15");
        commodity.setPrice(Math.random() * 1000);
        commodity.setDescription("爱疯14");
        commodityService.insertCommodity(commodity);

    }



}
