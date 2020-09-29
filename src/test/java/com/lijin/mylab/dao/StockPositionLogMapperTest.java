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
public class StockPositionLogMapperTest {

	@Autowired
	private StockPositionLogMapper mapper;
	
	@Test
	public void testSelectByParamMap() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("positionId", 4);
		paramMap.put("status", "1");
		List<StockPositionLog> lst = mapper.selectByParamMap(null);
		
		for (StockPositionLog spl : lst) {
			System.out.println(spl.getLogId() + "_" + spl.getBuyInDt() + "_" + spl.getBuyInAt() + "_" + spl.getSellOutDt() + "_" + spl.getSellOutAt());
		}
	}
	
	@Test
	public void testSelectByLogId() {
		StockPositionLog spl = mapper.selectByLogId(6);
		System.out.println(spl.getLogId() + "_" + spl.getBuyInDt() + "_" + spl.getBuyInAt() + "_" + spl.getSellOutDt() + "_" + spl.getSellOutAt());
	}
	
	@Test
	public void testAdd() {
		StockPositionLog log = new StockPositionLog();
		log.setStockNo("sh600001");
		log.setTransNumber(600);
		log.setBuyInDt("20170831");
		log.setBuyInAt(964);
		log.setSellOutDt("");
		log.setSellOutAt(0);
		log.setStatus("0");
		mapper.add(log);
	}
	
	@Test
	public void testUpd() {
		StockPositionLog spl = mapper.selectByLogId(13);
		spl.setSellOutAt(684);
		spl.setSellOutDt("20170830");
		spl.setStatus("1");
		mapper.upd(spl);
	}
	
	@Test
	public void testDelete() {
		mapper.delete(20);
	}
}
