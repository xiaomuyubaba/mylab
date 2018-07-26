package com.lijin.mylab.dao.mybatis.model;

/**
 * 持仓记录实体类
 * 
 * tbl_stock_position_log
 * 
 * @author xujin
 *
 */
public class StockPositionLog {

	private int logId; // 流水id
	private String stockNo; // 股票号码
	private int transNumber; // 股票交易数
	private String buyInDt; // 买入日期
	private int buyInAt; // 买入金额
	private String sellOutDt = ""; // 卖出日期
	private int sellOutAt = 0; // 卖出金额
	private String status = "0"; // 状态 0 - 持仓中，1 - 已卖出
	
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public int getTransNumber() {
		return transNumber;
	}
	public void setTransNumber(int transNumber) {
		this.transNumber = transNumber;
	}
	public String getBuyInDt() {
		return buyInDt;
	}
	public void setBuyInDt(String buyInDt) {
		this.buyInDt = buyInDt;
	}
	public int getBuyInAt() {
		return buyInAt;
	}
	public void setBuyInAt(int buyInAt) {
		this.buyInAt = buyInAt;
	}
	public String getSellOutDt() {
		return sellOutDt;
	}
	public void setSellOutDt(String sellOutDt) {
		this.sellOutDt = sellOutDt;
	}
	public int getSellOutAt() {
		return sellOutAt;
	}
	public void setSellOutAt(int sellOutAt) {
		this.sellOutAt = sellOutAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	
}
