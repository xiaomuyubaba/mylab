package com.lijin.mylab.controller;

import com.lijin.mylab.cache.StockInfoCache;
import com.lijin.mylab.cache.StockPriceCache;
import com.lijin.mylab.dao.StockInfoDAO;
import com.lijin.mylab.dao.StockPositionLogDAO;
import com.lijin.mylab.dao.mybatis.model.StockInfo;
import com.lijin.mylab.dao.mybatis.model.StockPositionLog;
import com.lijin.mylab.entity.AjaxResult;
import com.lijin.mylab.enums.PositionLogStatus;
import com.lijin.mylab.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private StockPositionLogDAO stockPositionLogDAO;
	@Autowired
	private StockInfoDAO stockInfoDAO;
	
	@GetMapping("/position/mng")
	public String positionMng() {
		return "stock/positionMng";
	}
	
	@PostMapping("/position/qry")
	public @ResponseBody AjaxResult qryPosition() {
		Map<String, String> qryParamMap = this.getQryParamMap();
		qryParamMap.put("status", PositionLogStatus._0.getCode());
		List<StockPositionLog> pLst = stockPositionLogDAO.select(qryParamMap);

		stockPriceCache.refresh();
		List<Map<String, String>> lst = commonBO.transferLst(pLst, this::transferHoldingStockPositionLog);;
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
		
		return buildSuccResp("logLst", lst);
	}
	
	@GetMapping("/position/buy")
	public String buy(Model model) {
		model.addAttribute("stockList", stockInfoCache.getStockList());
		model.addAttribute("today", DateUtil.now8());
		return "stock/buy";
	}
	
	@PostMapping("/position/buy/submit")
	public @ResponseBody AjaxResult buySubmit(String stockNo, String buyInDt, int buyInNum, String buyInAt) {
		StockPositionLog log = new StockPositionLog();
		log.setStockNo(stockNo);
		log.setBuyInAt(new BigDecimal(buyInAt).movePointRight(2).intValue());
		log.setBuyInDt(buyInDt);
		log.setTransNumber(buyInNum);
		log.setSellOutAt(0);
		log.setSellOutDt("");
		log.setStatus(PositionLogStatus._0.getCode());
		logger.info("before added logId: {}", log.getLogId());
		stockPositionLogDAO.add(log);
		logger.info("after added logId: {}", log.getLogId());
		return buildSuccResp();
	}
	
	@GetMapping("/position/sell")
	public String sell(Model model, int logId) {
		StockPositionLog log = stockPositionLogDAO.selectByLogId(logId);
		Map<String, String> logInfo = commonBO.transferEntity(log, this::transferHoldingStockPositionLog);
		model.addAttribute("logInfo", logInfo);
		model.addAttribute("today", DateUtil.now8());
		return "stock/sell";
	}

	@PostMapping("/position/sell/submit")
	public @ResponseBody AjaxResult sellSubmit(int logId, String sellOutDt, String sellOutAt) {
		StockPositionLog log = stockPositionLogDAO.selectByLogId(logId);
		if (log == null) {
			return buildErrorResp("未找到持仓记录:" + logId);
		}
		log.setSellOutDt(sellOutDt);
		log.setSellOutAt(new BigDecimal(sellOutAt).movePointRight(2).intValue());
		log.setStatus(PositionLogStatus._1.getCode());
		stockPositionLogDAO.upd(log);
		return buildSuccResp();
	}

	@PostMapping("/delLog")
	public @ResponseBody AjaxResult delLog(int logId) {
		StockPositionLog log = stockPositionLogDAO.selectByLogId(logId);
		if (log == null) {
			return buildErrorResp("未找到持仓记录:" + logId);
		}
		stockPositionLogDAO.del(logId);
		return buildSuccResp();
	}
	
	@PostMapping("/delPositionLog")
	public @ResponseBody AjaxResult delPositionLog(int logId) {
		stockPositionLogDAO.del(logId);;
		return buildSuccResp();
	}
	
	@GetMapping(path = "/hisLog/mng")
	public String hisLogMng() {
		return "stock/hisLogMng";
	}

	@PostMapping("/hisLog/qry")
	public @ResponseBody AjaxResult qryHisLog() {
		// 查询仓位情况列表
		Map<String, String> qryParamMap = this.getQryParamMap();
		qryParamMap.put("status", PositionLogStatus._1.getCode());
		List<StockPositionLog> pLst = stockPositionLogDAO.select(qryParamMap);
		List<Map<String, String>> lst = commonBO.transferLst(pLst, this::transferHisStockPositionLog);;

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

		return buildSuccResp("logLst", lst);
	}
	
	@GetMapping("/stock/mng")
	public String stockMng() {
		return "stock/stockMng";
	}
	
	
	@PostMapping("/stock/qry")
	public @ResponseBody AjaxResult qryStock() {
		List<StockInfo> stockLst = stockInfoCache.getStockList();
		return buildSuccResp("stockLst", commonBO.transferLst(stockLst, null));
	}
	
	@GetMapping("/stock/add")
	public String addStock(Model model) {
		return "stock/addStock";
	}
	
	@PostMapping("/stock/add/submit")
	public @ResponseBody AjaxResult addStockSubmit(String stockNo, String stockNm) {
		if (!stockInfoCache.isExist(stockNo)) {
			StockInfo stockInfo = new StockInfo();
			stockInfo.setStockNo(stockNo.toUpperCase());
			stockInfo.setStockNm(stockNm);
			stockInfoDAO.add(stockInfo);
			stockInfoCache.refresh();
		} else {
			logger.info(stockNo + " 已存在，无需重复添加");
		}
		return buildSuccResp();
	}

	@PostMapping("/stock/del")
	public @ResponseBody AjaxResult delStock(String stockNo) {
		Map<String, String> qryParamMap = new HashMap<>();
		qryParamMap.put("stockNo", stockNo);
		if (stockPositionLogDAO.count(qryParamMap) > 0) {
			return buildErrorResp(stockNo + "有持仓记录，删除失败");
		}
		stockInfoDAO.del(stockNo);
		stockInfoCache.refresh();
		return buildSuccResp();
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
