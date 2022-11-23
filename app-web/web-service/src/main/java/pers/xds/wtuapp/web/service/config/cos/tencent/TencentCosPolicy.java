package pers.xds.wtuapp.web.service.config.cos.tencent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-22 22:16
 */
public class TencentCosPolicy {

    public String version = "2.0";

    public List<CosStatement> statement;

    public TencentCosPolicy(CosStatement statement) {
        this.statement = new ArrayList<>(1);
        this.statement.add(statement);
    }

}
