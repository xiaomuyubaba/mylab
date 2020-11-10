package com.lijin.mylab.dao;

import com.lijin.mylab.dao.mybatis.model.StockInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StockInfoDAOTest {

	@Autowired
	private StockInfoDAO dao;
	
	@Test
	public void testAdd() {
		StockInfo entity = new StockInfo();
		entity.setStockNo("sh600611");
		entity.setStockNm("大众交通");
		dao.add(entity);
	}
	
	@Test
	public void testSelectAll() {
		List<StockInfo> lst = dao.selectAll();
		System.out.println(Arrays.toString(lst.toArray()));
	}
	
}
