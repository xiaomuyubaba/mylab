package com.lijin.mylab.dao.mybatis.model;

/**
 * 股票信息
 * 
 * tbl_stock_info
 * 
 * @author xujin
 *
 */
public class StockInfo {

	private String stockNo; // 股票号码
	private String stockNm; // 股票名称
	
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public String getStockNm() {
		return stockNm;
	}
	public void setStockNm(String stockNm) {
		this.stockNm = stockNm;
	}
}
