package pers.xds.wtuapp.web.es.dao;

/**
 * @author DeSen Xu
 * @date 2023-02-02 16:19
 */
public interface EsCommodityDao {

    String INDEX_NAME = "commodity";

    /**
     * 让某件商品的商品数量自增
     * @param commodityId 商品id
     * @param increment 要自增的数量
     */
    void increaseCommodityCount(int commodityId, int increment);

    /**
     * 设置商品数量
     * @param commodityId 商品id
     * @param count 要设置的数量
     */
    void updateCommodityCount(int commodityId, int count);


}
