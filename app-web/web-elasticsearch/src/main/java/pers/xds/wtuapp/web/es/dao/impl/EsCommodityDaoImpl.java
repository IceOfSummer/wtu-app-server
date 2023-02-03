package pers.xds.wtuapp.web.es.dao.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.InlineScript;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.json.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.web.es.bean.EsCommodity;
import pers.xds.wtuapp.web.es.dao.EsCommodityDao;

import java.io.IOException;

/**
 * @author DeSen Xu
 * @date 2023-02-02 16:20
 */
@Component
public class EsCommodityDaoImpl implements EsCommodityDao {

    private ElasticsearchClient elasticsearchClient;

    @Autowired
    public void setElasticsearchClient (ElasticsearchClient  elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public void increaseCommodityCount(int commodityId, int increment) {
        InlineScript inlineScript = new InlineScript.Builder()
                .source("ctx._source.count += params.incr")
                .params("incr", JsonData.of(increment))
                .build();
        Script script = new Script.Builder().inline(inlineScript).build();
        UpdateRequest<EsCommodity, Object> updateRequest = new UpdateRequest.Builder<EsCommodity, Object>()
                .id(String.valueOf(commodityId))
                .index(INDEX_NAME)
                .script(script)
                .build();
        try {
            elasticsearchClient.update(updateRequest, EsCommodity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCommodityCount(int commodityId, int count) {
        EsCommodity esCommodity = new EsCommodity();
        esCommodity.setCount(count);
        UpdateRequest<EsCommodity, EsCommodity> updateRequest = new UpdateRequest.Builder<EsCommodity, EsCommodity>()
                .id(String.valueOf(commodityId))
                .index(INDEX_NAME)
                .doc(esCommodity)
                .build();
        try {
            elasticsearchClient.update(updateRequest, EsCommodity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
