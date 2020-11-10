package com.lijin.mylab.dao;

import com.lijin.mylab.dao.mybatis.mapper.StockPositionLogMapper;
import com.lijin.mylab.dao.mybatis.model.StockPositionLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StockPositionLogDAOTest {

    @Autowired
    private StockPositionLogDAO dao;

    @Test
    public void selectTest() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("status", "0");
        List<StockPositionLog> lst = dao.select(null);

        for (StockPositionLog spl : lst) {
            System.out.println(spl.getLogId() + "_" + spl.getBuyInDt() + "_" + spl.getBuyInAt() + "_" + spl.getSellOutDt() + "_" + spl.getSellOutAt());
        }
    }

    @Test
    public void selectByLogIdTest() {
        StockPositionLog spl = dao.selectByLogId(6);
        System.out.println(spl.getLogId() + "_" + spl.getBuyInDt() + "_" + spl.getBuyInAt() + "_" + spl.getSellOutDt() + "_" + spl.getSellOutAt());
    }

    @Test
    public void addTest() {
        StockPositionLog log = new StockPositionLog();
        log.setStockNo("sh600001");
        log.setTransNumber(600);
        log.setBuyInDt("20170831");
        log.setBuyInAt(964);
        log.setSellOutDt("");
        log.setSellOutAt(0);
        log.setStatus("0");
        dao.add(log);
    }

    @Test
    public void updTest() {
        StockPositionLog spl = dao.selectByLogId(13);
        spl.setSellOutAt(684);
        spl.setSellOutDt("20170830");
        spl.setStatus("1");
        dao.upd(spl);
    }

    @Test
    public void delTest() {
        dao.del(20);
    }
}
