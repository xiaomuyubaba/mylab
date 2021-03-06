package com.lijin.mylab.dao.mybatis.mapper;

import com.lijin.mylab.dao.mybatis.model.StockInfo;
import com.lijin.mylab.dao.mybatis.model.StockInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    long countByExample(StockInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    int deleteByExample(StockInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    int deleteByPrimaryKey(String stockNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    int insert(StockInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    int insertSelective(StockInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    List<StockInfo> selectByExample(StockInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    StockInfo selectByPrimaryKey(String stockNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    int updateByExampleSelective(@Param("record") StockInfo record, @Param("example") StockInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    int updateByExample(@Param("record") StockInfo record, @Param("example") StockInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    int updateByPrimaryKeySelective(StockInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_stock_info
     *
     * @mbg.generated Thu Nov 12 19:45:26 CST 2020
     */
    int updateByPrimaryKey(StockInfo record);
}