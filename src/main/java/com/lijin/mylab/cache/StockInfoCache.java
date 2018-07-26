package com.lijin.mylab.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lijin.mylab.dao.mybatis.mapper.StockInfoMapper;
import com.lijin.mylab.dao.mybatis.model.StockInfo;

@Component
public class StockInfoCache implements InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(StockInfoCache.class);
	
	@Autowired
	private StockInfoMapper stockInfoMapper;
	
	private List<StockInfo> stockList = new ArrayList<>();
	private Map<String, String> stockMap = new HashMap<>();
	
	/**
	 * 刷新
	 */
	public void refresh() {
		this.init();
	}
	
	/**
	 * 初始化
	 */
	private synchronized void init() {
		logger.info("##init stock info cache started##");
		stockList = stockInfoMapper.selectAll();
		stockMap = new LinkedHashMap<String, String>();
		for (StockInfo stockInfo : stockList) {
			stockMap.put(stockInfo.getStockNo(), stockInfo.getStockNm());
		}
		logger.info("##init stock info cache ended, total num:" + stockList.size() + "##");
	}

	public List<StockInfo> getStockList() {
		if (stockList == null) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableList(stockList);
		}
	}
	
	public boolean isExist(String stockNo) {
		return stockMap.containsKey(stockNo.toUpperCase());
	}

	public Map<String, String> getStockMap() {
		if (stockMap == null || stockMap.isEmpty()) {
			return Collections.emptyMap();
		} else {
			return Collections.unmodifiableMap(stockMap);
		}
	}
	
	public String getStockNm(String stockNo) {
		return (stockMap != null && !stockMap.isEmpty() && stockMap.containsKey(stockNo)) ?
				stockMap.get(stockNo) : "";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}
}
