package pers.xds.wtuapp.web.es.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import pers.xds.wtuapp.web.es.bean.EsCommodity;

/**
 * @author DeSen Xu
 * @date 2022-09-10 17:31
 */
@Repository
public interface CommodityRepository extends ElasticsearchRepository<EsCommodity, Integer> {

    /**
     * 根据商品名称搜索
     * @param name 商品名称
     * @param pageable 分页
     * @return 匹配的商品
     */
    Page<EsCommodity> searchByName(String name, Pageable pageable);



}
