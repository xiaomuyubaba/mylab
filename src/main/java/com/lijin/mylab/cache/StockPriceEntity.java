package com.lijin.mylab.cache;

public class StockPriceEntity {

	private int currPrice; // 股票当前价格
	private int currDayMaxPrice; // 今日最高价格
	private int currDayMinPrice; // 今日最低价格
	
	private int currMonMaxPrice; // 当月最高价格
	private int currMonMinPrice; // 当月最低价格
	private int lastMonMaxPrice; // 上月最高价格
	private int lastMonMinPrice; // 上月最低价格
	private int currYearMaxPrice; // 当年最高价格
	private int currYearMinPrice; // 当年最低价格
	private int lastYearMaxPrice; // 上年度最高价格
	private int lastYearMinPrice; // 上年度最低价格
	
	public int getCurrMonMaxPrice() {
		return currMonMaxPrice;
	}
	public void setCurrMonMaxPrice(int currMonMaxPrice) {
		this.currMonMaxPrice = currMonMaxPrice;
	}
	public int getCurrMonMinPrice() {
		return currMonMinPrice;
	}
	public void setCurrMonMinPrice(int currMonMinPrice) {
		this.currMonMinPrice = currMonMinPrice;
	}
	public int getLastMonMaxPrice() {
		return lastMonMaxPrice;
	}
	public void setLastMonMaxPrice(int lastMonMaxPrice) {
		this.lastMonMaxPrice = lastMonMaxPrice;
	}
	public int getLastMonMinPrice() {
		return lastMonMinPrice;
	}
	public void setLastMonMinPrice(int lastMonMinPrice) {
		this.lastMonMinPrice = lastMonMinPrice;
	}
	public int getCurrYearMaxPrice() {
		return currYearMaxPrice;
	}
	public void setCurrYearMaxPrice(int currYearMaxPrice) {
		this.currYearMaxPrice = currYearMaxPrice;
	}
	public int getCurrYearMinPrice() {
		return currYearMinPrice;
	}
	public void setCurrYearMinPrice(int currYearMinPrice) {
		this.currYearMinPrice = currYearMinPrice;
	}
	public int getLastYearMaxPrice() {
		return lastYearMaxPrice;
	}
	public void setLastYearMaxPrice(int lastYearMaxPrice) {
		this.lastYearMaxPrice = lastYearMaxPrice;
	}
	public int getLastYearMinPrice() {
		return lastYearMinPrice;
	}
	public void setLastYearMinPrice(int lastYearMinPrice) {
		this.lastYearMinPrice = lastYearMinPrice;
	}
	public int getCurrPrice() {
		return currPrice;
	}
	public void setCurrPrice(int currPrice) {
		this.currPrice = currPrice;
	}
	public int getCurrDayMaxPrice() {
		return currDayMaxPrice;
	}
	public void setCurrDayMaxPrice(int currDayMaxPrice) {
		this.currDayMaxPrice = currDayMaxPrice;
	}
	public int getCurrDayMinPrice() {
		return currDayMinPrice;
	}
	public void setCurrDayMinPrice(int currDayMinPrice) {
		this.currDayMinPrice = currDayMinPrice;
	}
	
}
