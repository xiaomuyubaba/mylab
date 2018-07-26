package com.lijin.mylab.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lijin.mylab.dao.mybatis.model.StockInfo;

public interface StockInfoMapper {

	public List<StockInfo> selectAll();

	public void add(StockInfo entity);
	
	public void batchAdd(@Param("lst")List<StockInfo> lst);
	
	public void delete(@Param("stockNo")String stockNo);

}
