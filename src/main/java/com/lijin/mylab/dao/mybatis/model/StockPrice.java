package com.lijin.mylab.dao.mybatis.model;

/**
 * 股票价格信息表
 * 
 * tbl_stock_price
 * 
 * @author xujin
 *
 */
public class StockPrice {

	private String stockNo; // 股票号码
	private String priceDt; // 价格日期
	private int openPrice; // 开盘价格
	private int maxPrice; // 当日最高价
	private int minPrice; // 当日最低价
	private int closePrice; // 收盘价格
	
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public String getPriceDt() {
		return priceDt;
	}
	public void setPriceDt(String priceDt) {
		this.priceDt = priceDt;
	}
	public int getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(int openPrice) {
		this.openPrice = openPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(int closePrice) {
		this.closePrice = closePrice;
	}
	
}
