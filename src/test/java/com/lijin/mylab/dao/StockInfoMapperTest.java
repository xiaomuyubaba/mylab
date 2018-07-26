package com.lijin.mylab.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lijin.mylab.dao.mybatis.mapper.StockInfoMapper;
import com.lijin.mylab.dao.mybatis.model.StockInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StockInfoMapperTest {

	@Autowired
	private StockInfoMapper mapper;
	
	@Test
	public void testAdd() {
		StockInfo entity = new StockInfo();
		entity.setStockNo("sh600611");
		entity.setStockNm("大众交通");
		mapper.add(entity);
	}
	
	@Test
	public void testSelectAll() {
		List<StockInfo> lst = mapper.selectAll();
		System.out.println(Arrays.toString(lst.toArray()));
	}
	
}
