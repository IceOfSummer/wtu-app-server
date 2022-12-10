package pers.xds.wtuapp.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xds.wtuapp.web.database.bean.Order;
import pers.xds.wtuapp.web.database.bean.TradeStat;
import pers.xds.wtuapp.web.database.bean.User;
import pers.xds.wtuapp.web.database.mapper.*;
import pers.xds.wtuapp.web.redis.CounterCache;
import pers.xds.wtuapp.web.service.CommodityService;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.es.bean.EsCommodity;
import pers.xds.wtuapp.web.es.repository.CommodityRepository;
import pers.xds.wtuapp.web.service.EmailService;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.ServiceCodeWrapper;
import pers.xds.wtuapp.web.service.config.email.CommodityLockTemplateData;
import pers.xds.wtuapp.web.service.util.DateFormatUtils;

import java.util.Date;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-09 18:01
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    /**
     * 每次分页显示的最大元素数量
     */
    private static final int MAX_PAGE_SIZE = 30;

    private CounterCache counterCache;


    private CommodityMapper commodityMapper;

    private CommodityRepository commodityRepository;

    private OrderMapper orderMapper;

    private UserTradeMapper userTradeMapper;

    private TradeStatMapper tradeStatMapper;

    private EmailService emailService;

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setCounterCache(CounterCache counterCache) {
        this.counterCache = counterCache;
    }

    @Autowired
    public void setTradeStatMapper(TradeStatMapper tradeStatMapper) {
        this.tradeStatMapper = tradeStatMapper;
    }

    @Autowired
    public void setUserTradeMapper(UserTradeMapper userTradeMapper) {
        this.userTradeMapper = userTradeMapper;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setCommodityRepository(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    @Autowired
    public void setCommodityMapper(CommodityMapper commodityMapper) {
        this.commodityMapper = commodityMapper;
    }

    /**
     * 每个人最多可以发布的商品数量
     */
    public static final int MAX_ACTIVE_COMMODITY = 10;

    public static final String INSERT_CACHE_KEY_PREFIX = "insertCommodity:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCodeWrapper<Integer> insertCommodity(Commodity commodity) {
        Integer ownerId = commodity.getOwnerId();
        if (ownerId == null) {
            return ServiceCodeWrapper.fail(ServiceCode.BAD_REQUEST);
        }
        String key = INSERT_CACHE_KEY_PREFIX + ownerId;
        // 每个周最多发布14件商品
        final int maxWeekPost = 14;
        int invokeCount = counterCache.getInvokeCount(key, CounterCache.Duration.WEEK);
        if (invokeCount > maxWeekPost) {
            return ServiceCodeWrapper.fail(ServiceCode.RATE_LIMIT);
        }

        Integer cnt = tradeStatMapper.selectSellingCount(ownerId);
        if (cnt == null) {
            TradeStat tradeStat = new TradeStat(ownerId);
            tradeStat.setSellingCount(1);
            tradeStatMapper.insert(tradeStat);
        } else if (cnt > MAX_ACTIVE_COMMODITY) {
            return ServiceCodeWrapper.fail(ServiceCode.NOT_AVAILABLE);
        } else {
            tradeStatMapper.modifySellingCount(ownerId, cnt + 1);
        }
        counterCache.increaseInvokeCountAsync(invokeCount, key);
        // 使用自动生成的id
        commodity.setCommodityId(null);
        commodityMapper.insert(commodity);
        commodityRepository.save(new EsCommodity(
                commodity.getCommodityId(),
                commodity.getName(),
                System.currentTimeMillis(),
                commodity.getPrice(),
                commodity.getPreviewImage(),
                commodity.getTradeLocation()
        ));
        return ServiceCodeWrapper.success(commodity.getCommodityId());
    }

    @Nullable
    @Override
    public Commodity queryCommodity(int commodityId) {
        return commodityMapper.selectById(commodityId);
    }


    private static final String LOCK_COMMODITY_PREFIX_KEY = "lockCommodity:";

    private static final int MAX_LOCK_ACTION_PER_DAY = 10;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceCodeWrapper<Integer> lockCommodity(int commodityId, int userId, int count, String remark) {
        Commodity commodity = commodityMapper.selectSimpleInfo(commodityId);
        if (commodity == null) {
            return ServiceCodeWrapper.fail(ServiceCode.NOT_EXIST);
        }
        if (commodity.getStatus() != Commodity.STATUS_ACTIVE || commodity.getOwnerId() == userId) {
            return ServiceCodeWrapper.fail(ServiceCode.NOT_AVAILABLE);
        }
        if (commodity.getCount() < count) {
            return ServiceCodeWrapper.fail(ServiceCode.BAD_REQUEST);
        }
        String key = LOCK_COMMODITY_PREFIX_KEY + userId;
        int invokeCount = counterCache.getInvokeCount(key);
        if (invokeCount > MAX_LOCK_ACTION_PER_DAY) {
            return ServiceCodeWrapper.fail(ServiceCode.RATE_LIMIT);
        }
        if (commodityMapper.updateCommodityCount(commodityId, commodity.getCount() - count, commodity.getVersion()) == 1) {
            int ownerId = commodity.getOwnerId();
            User user = userMapper.selectNameAndEmail(ownerId);
            String nickname = user.getNickname() == null ? String.valueOf(ownerId) : user.getNickname();
            // 添加交易记录
            Order order = new Order(commodityId, userId, remark, ownerId, count);
            // 可能会和数据库有点小偏差，不过应该差不了太多
            Date createTime = new Date();
            orderMapper.insert(order);
            userTradeMapper.addUserTrade(order.getOrderId(), userId, ownerId);

            // 发送邮件提醒
            emailService.sendCommodityLockTip(
                    ownerId,
                    user.getEmail(),
                    new CommodityLockTemplateData(
                            nickname,
                            commodity.getName(),
                            commodity.getPrice(),
                            commodity.getTradeLocation(),
                            DateFormatUtils.toLocalString(createTime),
                            order.getRemark(),
                            order.getCount(),
                            commodity.getPreviewImage()
                    )
            );
            counterCache.increaseInvokeCountAsync(invokeCount, key);
            return ServiceCodeWrapper.success(order.getOrderId());
        }
        return ServiceCodeWrapper.fail(ServiceCode.CONCURRENT_ERROR);
    }

    @Override
    public List<EsCommodity> searchCommodityByName(String commodityName, int page, int size) {
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
        org.springframework.data.domain.Page<EsCommodity> esCommodities =
                commodityRepository.searchByName(commodityName, Pageable.ofSize(size).withPage(page));
        return esCommodities.toList();
    }

    @Override
    public int querySellingCount(int uid) {
        Integer count = commodityMapper.getSellingCount(uid);
        return count == null ? 0 : count;
    }

    @Override
    public List<Commodity> queryUserCommodity(int uid, int page, int size) {
        Page<Commodity> pg = new Page<>(page, size);
        return commodityMapper.selectCommodityByUid(uid, pg).getRecords();
    }

    @Override
    public void updateCommodity(int ownerId, int commodityId, Commodity commodity) {
        commodityMapper.updateCommodity(ownerId, commodityId, commodity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void takeDownCommodity(int uid, int commodityId) {
        commodityMapper.updateCommodityStatus(uid, commodityId, Commodity.STATUS_INACTIVE);
        tradeStatMapper.decreaseSellingCount(uid);
    }

    @Override
    public List<Commodity> getRecommend(Integer maxId) {
        final int pageSize = 8;
        return commodityMapper.selectByTimeDesc(maxId, pageSize);
    }


}
