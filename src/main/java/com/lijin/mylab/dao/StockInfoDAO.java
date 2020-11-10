package com.lijin.mylab.dao;

import com.lijin.mylab.dao.mybatis.mapper.StockInfoMapper;
import com.lijin.mylab.dao.mybatis.model.StockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockInfoDAO extends BaseDAO {

    @Autowired
    private StockInfoMapper stockInfoMapper;

    public List<StockInfo> selectAll() {
        return stockInfoMapper.selectByExample(null);
    }

    public void del(String stockNo) {
        stockInfoMapper.deleteByPrimaryKey(stockNo);
    }

    public void add(StockInfo stockInfo) {
        stockInfoMapper.insert(stockInfo);
    }

}
