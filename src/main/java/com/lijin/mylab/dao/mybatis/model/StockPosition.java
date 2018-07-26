package com.lijin.mylab.dao.mybatis.model;

import java.util.Date;

/**
 * 股票持仓信息
 * @author xujin
 *
 */
public class StockPosition {

	private int seqId;
	private String stockNo; // 股票号
	private String stockNm; // 股票名称
	private int position; // 持仓数量
	private int currCost; // 当前成本
	private int profitAmt; // 盈利金额（如果亏损则为负）
	private Date lastUpdTs; // 最近更新时间
	
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
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
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Date getLastUpdTs() {
		return lastUpdTs;
	}
	public void setLastUpdTs(Date lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}
	public int getCurrCost() {
		return currCost;
	}
	public void setCurrCost(int currCost) {
		this.currCost = currCost;
	}
	public int getProfitAmt() {
		return profitAmt;
	}
	public void setProfitAmt(int profitAmt) {
		this.profitAmt = profitAmt;
	}
	
}
