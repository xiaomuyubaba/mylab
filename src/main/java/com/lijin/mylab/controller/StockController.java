package com.lijin.mylab.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lijin.mylab.cache.StockInfoCache;
import com.lijin.mylab.cache.StockPriceCache;
import com.lijin.mylab.dao.mybatis.mapper.StockInfoMapper;
import com.lijin.mylab.dao.mybatis.mapper.StockPositionLogMapper;
import com.lijin.mylab.dao.mybatis.model.StockInfo;
import com.lijin.mylab.dao.mybatis.model.StockPositionLog;
import com.lijin.mylab.entity.AjaxResult;
import com.lijin.mylab.enums.PositionLogStatus;

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
	
	@RequestMapping(path = "/position/logLst", method = RequestMethod.POST)
	public @ResponseBody AjaxResult positionLogLst(String status) {
		// 查询仓位情况列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		List<StockPositionLog> pLst = stockPositionLogMapper.selectByParamMap(paramMap);
		
		if (PositionLogStatus._0.getCode().equals(status)) {
			stockPriceCache.refresh();
		}
		
		List<Map<String, String>> lst = null;
		
		if (PositionLogStatus._0.getCode().equals(status)) {
			lst = commonBO.transferLst(pLst, this::transferHoldingStockPositionLog);
		} else if (PositionLogStatus._1.getCode().equals(status)) {
			lst = commonBO.transferLst(pLst, this::transferHisStockPositionLog);
		} else {
			return commonBO.buildErrorResp("非法状态");
		}
		
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
		
		return commonBO.buildSuccResp("logLst", lst);
	}
	
	@RequestMapping(path = "/stockLst", method = RequestMethod.POST)
	public @ResponseBody AjaxResult stockLst() {
		List<StockInfo> stockLst = stockInfoCache.getStockList();
		return commonBO.buildSuccResp("stockLst", commonBO.transferLst(stockLst, null));
	}
	
	@RequestMapping(path = "/addStock", method = RequestMethod.GET)
	public String addStock() {
		return "stock/addStock";
	}
	
	@RequestMapping(path = "/addStock/submit", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addStockSubmit(String stockNo, String stockNm) {
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
		return commonBO.buildSuccResp();
	}

	@RequestMapping(path = "/delStock", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delStock(String stockNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("stockNo", stockNo);
		if (stockPositionLogMapper.countByParamMap(paramMap) > 0) {
			return commonBO.buildErrorResp(stockNo + "有持仓记录，删除失败");
		}
		
		StockInfoMapper.delete(stockNo);
		stockInfoCache.refresh();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(path = "/buy", method = RequestMethod.GET)
	public String buy(Model model) {
		model.addAttribute("stockList", stockInfoCache.getStockList());
		return "stock/buy";
	}
	
	@RequestMapping(path = "/buy/submit", method = RequestMethod.POST)
	public @ResponseBody AjaxResult buySubmit(String stockNo, String buyInDt, int buyInNum, String buyInAt) {
		StockPositionLog log = new StockPositionLog();
		log.setStockNo(stockNo);
		log.setBuyInAt(new BigDecimal(buyInAt).movePointRight(2).intValue());
		log.setBuyInDt(buyInDt);
		log.setTransNumber(buyInNum);
		stockPositionLogMapper.add(log);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(path = "/sell", method = RequestMethod.GET)
	public String sell(Model model, int logId) {
		StockPositionLog log = stockPositionLogMapper.selectByLogId(logId);
		Map<String, String> logInfo = commonBO.transferEntity(log, this::transferHoldingStockPositionLog);
		model.addAttribute("logInfo", logInfo);
		return "stock/sell";
	}

	@RequestMapping(path = "/sell/submit", method = RequestMethod.POST)
	public @ResponseBody AjaxResult sellSubmit(int logId, String sellOutDt, String sellOutAt) {
		StockPositionLog log = stockPositionLogMapper.selectByLogId(logId);
		if (log == null) {
			return commonBO.buildErrorResp("未找到持仓记录:" + logId);
		}
		log.setSellOutDt(sellOutDt);
		log.setSellOutAt(new BigDecimal(sellOutAt).movePointRight(2).intValue());
		log.setStatus(PositionLogStatus._1.getCode());
		stockPositionLogMapper.upd(log);
		return commonBO.buildSuccResp();
	}

	@RequestMapping(path = "/delLog", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delLog(int logId) {
		StockPositionLog log = stockPositionLogMapper.selectByLogId(logId);
		if (log == null) {
			return commonBO.buildErrorResp("未找到持仓记录:" + logId);
		}
		stockPositionLogMapper.delete(logId);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(path = "/delPositionLog", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delPositionLog(int logId) {
		stockPositionLogMapper.delete(logId);;
		return commonBO.buildSuccResp();
	}
	
	private void transferHoldingStockPositionLog(Map<String, String> m) {
		String stockNo = m.get("stockNo");
		m.put("stockNm", stockInfoCache.getStockNm(stockNo));
		
		int buyInAt = Integer.parseInt(m.get("buyInAt"));
		BigDecimal bd = new BigDecimal(buyInAt).movePointLeft(2);
		m.put("buyInAt", bd.toPlainString());
		
		BigDecimal currPrice = stockPriceCache.getCurrPrice(stockNo);
		m.put("currPrice", currPrice.toPlainString());
		m.put("profit", currPrice.subtract(bd).divide(bd, 4, RoundingMode.UP).movePointRight(2).toPlainString());
		
		int prepareSellOutAt = buyInAt * 106 / 100;
		m.put("prepareSellOutAt", new BigDecimal(prepareSellOutAt).movePointLeft(2).toPlainString());
		
		int prepareBuyInAt = buyInAt * 95 / 100;
		m.put("prepareBuyInAt", new BigDecimal(prepareBuyInAt).movePointLeft(2).toPlainString());
	}
	
	private void transferHisStockPositionLog(Map<String, String> m) {
		String stockNo = m.get("stockNo");
		m.put("stockNm", stockInfoCache.getStockNm(stockNo));
		
		int buyInAt = Integer.parseInt(m.get("buyInAt"));
		BigDecimal bd = new BigDecimal(buyInAt).movePointLeft(2);
		m.put("buyInAt", bd.toPlainString());
		
		int sellOutAt = Integer.parseInt(m.get("sellOutAt"));
		BigDecimal bd2 = new BigDecimal(sellOutAt).movePointLeft(2);
		m.put("sellOutAt", bd2.toPlainString());
		m.put("profit", bd2.subtract(bd).divide(bd, 4, RoundingMode.UP).movePointRight(2).toPlainString());
		
		int transNumber = Integer.parseInt(m.get("transNumber"));
		m.put("profitAt", new BigDecimal((sellOutAt - buyInAt) * transNumber - 5 - 5).movePointLeft(2).toPlainString());
	}

}
