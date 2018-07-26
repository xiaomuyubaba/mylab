package com.lijin.mylab.dao.mybatis.model;

public class StockMaxAndMinPrice {

	private String stockNo; // 股票号码
	private int maxPrice; // 当日最高价
	private int minPrice; // 当日最低价
	
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
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
	
}
