package com.lijin.mylab.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lijin.mylab.dao.mybatis.model.StockPositionLog;

public interface StockPositionLogMapper {
	
	public int countByParamMap(@Param("qryParamMap") Map<String, Object> paramMap);
	
	public List<StockPositionLog> selectByParamMap(@Param("qryParamMap") Map<String, Object> paramMap);
	
	public StockPositionLog selectByLogId(int logId);
	
	public void add(StockPositionLog entity);
	
	public void upd(StockPositionLog entity);

	public void delete(int logId);
}
