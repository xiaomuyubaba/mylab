package com.lijin.mylab.cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.lijin.mylab.dao.mybatis.mapper.StockPositionLogMapper;
import com.lijin.mylab.dao.mybatis.model.StockPositionLog;

@Component
@Configurable
public class StockPriceCache {
	
	private static final Logger logger = Logger.getLogger(StockPriceCache.class);
	
	private Map<String, BigDecimal> priceCache = new ConcurrentHashMap<String, BigDecimal>();
	
	@Autowired
	private StockPositionLogMapper stockPositionLogMapper;
	
	public BigDecimal getCurrPrice(String stockNo) {
		return priceCache.get(stockNo);
	}
	
	public void init() {
		this.refresh();
	}

    public void refresh(){
    	logger.info("刷新股票价格开始");
    	
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("status", "0");
    	List<StockPositionLog> pLst = stockPositionLogMapper.selectByParamMap(paramMap);
		Map<String, BigDecimal> priceCacheTmp = new ConcurrentHashMap<String, BigDecimal>();
		pLst.forEach((log) -> {
			String stockNo = log.getStockNo();
			if (!priceCacheTmp.containsKey(stockNo)) {
				priceCacheTmp.put(stockNo, fetchStockPrice(stockNo));
			}
		});
		priceCache = priceCacheTmp;
    	
    	logger.info("刷新股票价格完成");
    }

    private BigDecimal fetchStockPrice(String stockNo) {
		try {
			logger.info("fetch stock price started:" + stockNo);
			HttpGet hg = new HttpGet("http://hq.sinajs.cn/list=" + stockNo.toLowerCase());
			CloseableHttpResponse response = HttpClients.createDefault().execute(hg);
			String str = EntityUtils.toString(response.getEntity());
			String stockPriceInfo = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
			String[] strs = stockPriceInfo.split(",");
			logger.info("fetch stock price finished:" + stockNo + "curr price is:" + strs[3]);
			return new BigDecimal(strs[3]).setScale(2);
		} catch (Exception e) {
			logger.error("fetch stock price failed:" + stockNo, e);
			return null;
		}
	}
}
