package com.lijin.mylab.controller;

import com.lijin.mylab.cache.StockInfoCache;
import com.lijin.mylab.cache.StockPriceCache;
import com.lijin.mylab.dao.mybatis.mapper.StockInfoMapper;
import com.lijin.mylab.dao.mybatis.mapper.StockPositionLogMapper;
import com.lijin.mylab.dao.mybatis.model.StockInfo;
import com.lijin.mylab.dao.mybatis.model.StockPositionLog;
import com.lijin.mylab.entity.AjaxResult;
import com.lijin.mylab.entity.IEntityTransfer;
import com.lijin.mylab.enums.PositionLogStatus;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Controller
@RequestMapping("/stock")
public class StockController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	private StockInfoCache stockInfoCache;
	@Autowired
	private StockPriceCache stockPriceCache;
	@Autowired
	private StockPositionLogMapper stockPositionLogMapper;
	@Autowired
	private StockInfoMapper StockInfoMapper;
	
	@RequestMapping(path = "/index", method = RequestMethod.GET)
	public String index() {
		return "stock/index";
	}
	
	@RequestMapping(path = "/position/logLst", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> positionLogLst(String status) {
		// 查询仓位情况列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		List<StockPositionLog> pLst = stockPositionLogMapper.selectByParamMap(paramMap);
		
		if (PositionLogStatus._0.getCode().equals(status)) {
			stockPriceCache.refresh();
		}
		
		List<Map<String, String>> lst = transferLst(pLst, new IEntityTransfer() {
			@Override
			public void transfer(Map<String, String> m) {
				String stockNo = m.get("stockNo");
				m.put("stockNm", stockInfoCache.getStockNm(stockNo));
				
				int buyInAt = Integer.parseInt(m.get("buyInAt"));
				BigDecimal bd = new BigDecimal(buyInAt).movePointLeft(2);
				m.put("buyInAt", bd.toPlainString());
				
				if (PositionLogStatus._0.getCode().equals(status)) {
					BigDecimal currPrice = stockPriceCache.getCurrPrice(stockNo);
					m.put("currPrice", currPrice.toPlainString());
					m.put("profit", currPrice.subtract(bd).divide(bd, 4, RoundingMode.UP).movePointRight(2).toPlainString());
					
					int prepareSellOutAt = buyInAt * 105 / 100 + 10;
					m.put("prepareSellOutAt", new BigDecimal(prepareSellOutAt).movePointLeft(2).toPlainString());
					
					int prepareBuyInAt = buyInAt * 95 / 100 - 10;
					m.put("prepareBuyInAt", new BigDecimal(prepareBuyInAt).movePointLeft(2).toPlainString());
				} else if (PositionLogStatus._1.getCode().equals(status)) {
					int sellOutAt = Integer.parseInt(m.get("sellOutAt"));
					BigDecimal bd2 = new BigDecimal(sellOutAt).movePointLeft(2);
					m.put("sellOutAt", bd2.toPlainString());
					m.put("profit", bd2.subtract(bd).divide(bd, 4, RoundingMode.UP).movePointRight(2).toPlainString());
					
					int transNumber = Integer.parseInt(m.get("transNumber"));
					m.put("profitAt", new BigDecimal((sellOutAt - buyInAt) * transNumber - 5 - 5).movePointLeft(2).toPlainString());
				}
			}
		});
		
		Collections.sort(lst, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				String status = o1.get("status");
				if (PositionLogStatus._0.getCode().equals(status)) {
					BigDecimal bd1 = new BigDecimal(o1.get("profit"));
					BigDecimal bd2 = new BigDecimal(o2.get("profit"));
					return bd2.compareTo(bd1);
				} else {
					return o2.get("sellOutDt").compareTo(o1.get("sellOutDt"));
				}
			}
		});
		
		return lst;
	}

	@RequestMapping(path = "/stockLst", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> stockLst() {
		List<StockInfo> stockLst = stockInfoCache.getStockList();
		List<Map<String, String>> lst = transferLst(stockLst, null);
		return lst;
	}
	
	@RequestMapping(path = "/addStock", method = RequestMethod.GET)
	public String addStock() {
		return "stock/addStock";
	}
	
	@RequestMapping(path = "/addStock/submit", method = RequestMethod.POST)
	public @ResponseBody String addStockSubmit(String stockNo, String stockNm) {
		if (!stockInfoCache.isExist(stockNo)) {
			StockInfo stockInfo = new StockInfo();
			stockInfo.setStockNo(stockNo.toUpperCase());
			stockInfo.setStockNm(stockNm);
			StockInfoMapper.add(stockInfo);
			stockInfoCache.refresh();
			stockPriceCache.refresh();
		} else {
			logger.info(stockNo + " 已存在，无需重复添加");
		}
		return "succ";
	}

	@RequestMapping(path = "/delStock", method = RequestMethod.POST)
	public @ResponseBody String delStock(String stockNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("stockNo", stockNo);
		if (stockPositionLogMapper.countByParamMap(paramMap) > 0) {
			return stockNo + "有持仓记录，删除失败";
		}
		StockInfoMapper.delete(stockNo);
		stockInfoCache.refresh();
		return "succ";
	}
	
	@RequestMapping(path = "/buy", method = RequestMethod.GET)
	public String buy(Model model) {
		model.addAttribute("stockList", stockInfoCache.getStockList());
		return "stock/buy";
	}
	
	@RequestMapping(path = "/buy/submit", method = RequestMethod.POST)
	public @ResponseBody String buySubmit(String stockNo, String buyInDt, int buyInNum, String buyInAt) {
		try {
			StockPositionLog log = new StockPositionLog();
			log.setStockNo(stockNo);
			log.setBuyInAt(new BigDecimal(buyInAt).movePointRight(2).intValue());
			log.setBuyInDt(buyInDt);
			log.setTransNumber(buyInNum);
			stockPositionLogMapper.add(log);
			return "succ";
		} catch (Exception e) {
			logger.error("buy failed", e);
			return "fail";
		}
	}
	
	@RequestMapping(path = "/sell", method = RequestMethod.GET)
	public String sell(Model model, int logId) {
		try {
			StockPositionLog log = stockPositionLogMapper.selectByLogId(logId);
			Map<String, String> logInfo = BeanUtils.describe(log);
			logInfo.put("stockNm", stockInfoCache.getStockNm(log.getStockNo()));
			model.addAttribute("logInfo", logInfo);
			BigDecimal bd = new BigDecimal(log.getBuyInAt()).movePointLeft(2);
			logInfo.put("buyInAt", bd.toPlainString());
			return "stock/sell";
		} catch (Exception e) {
			logger.error("查找记录失败", e);
			return "fail";
		}
	}

	@RequestMapping(path = "/sell/submit", method = RequestMethod.POST)
	public @ResponseBody String sellSubmit(int logId, String sellOutDt, String sellOutAt) {
		try {
			StockPositionLog log = stockPositionLogMapper.selectByLogId(logId);
			if (log == null) {
				return "未找到持仓记录:" + logId;
			}
			log.setSellOutDt(sellOutDt);
			log.setSellOutAt(new BigDecimal(sellOutAt).movePointRight(2).intValue());
			log.setStatus(PositionLogStatus._1.getCode());
			stockPositionLogMapper.upd(log);
			return "succ";
		} catch (Exception e) {
			logger.error("sell failed!!", e);
			return "fail";
		}
	}

	@RequestMapping(path = "/delLog", method = RequestMethod.POST)
	public @ResponseBody String delLog(int logId) {
		try {
			StockPositionLog log = stockPositionLogMapper.selectByLogId(logId);
			if (log == null) {
				return "未找到持仓记录:" + logId;
			}
			stockPositionLogMapper.delete(logId);
			return "succ";
		} catch (Exception e) {
			logger.error("sell failed!!", e);
			return "fail";
		}
	}
	
	@RequestMapping(path = "/delPositionLog", method = RequestMethod.POST)
	public @ResponseBody String delPositionLog(int logId) {
		stockPositionLogMapper.delete(logId);;
		return "succ";
	}

}
