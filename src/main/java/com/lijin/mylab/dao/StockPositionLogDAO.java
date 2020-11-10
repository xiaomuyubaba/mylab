package com.lijin.mylab.dao;

import com.lijin.mylab.dao.mybatis.mapper.StockPositionLogMapper;
import com.lijin.mylab.dao.mybatis.model.StockPositionLog;
import com.lijin.mylab.dao.mybatis.model.StockPositionLogExample;
import com.lijin.mylab.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class StockPositionLogDAO extends BaseDAO {

    @Autowired
    private StockPositionLogMapper stockPositionLogMapper;

    public List<StockPositionLog> select(Map<String, String> qryParamMap) {
        return stockPositionLogMapper.selectByExample(this.buildExample(qryParamMap));
    }

    public int count(Map<String, String> qryParamMap) {
        return (int) stockPositionLogMapper.countByExample(this.buildExample(qryParamMap));
    }

    public StockPositionLog selectByLogId(int logId) {
        return stockPositionLogMapper.selectByPrimaryKey(logId);
    }

    public void add(StockPositionLog log) {
        stockPositionLogMapper.insert(log);
    }

    public void del(int logId) {
        stockPositionLogMapper.deleteByPrimaryKey(logId);
    }

    public void upd(StockPositionLog log) {
        stockPositionLogMapper.updateByPrimaryKey(log);
    }

    private StockPositionLogExample buildExample(Map<String, String> qryParamMap) {
        StockPositionLogExample example = new StockPositionLogExample();
        if (qryParamMap != null && !qryParamMap.isEmpty()) {
            StockPositionLogExample.Criteria c = example.createCriteria();

            // 查询条件 - status
            String status = qryParamMap.get("status");
            if (StringUtil.isNotBlank(status)) {
                c.andStatusEqualTo(status);
            }

            String stockNo = qryParamMap.get("stockNo");
            if (StringUtil.isNotBlank(stockNo)) {
                c.andStockNoEqualTo(stockNo);
            }
        }

        return example;
    }
}
