package com.lijin.mylab.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lijin.mylab.dao.mybatis.mapper.StockInfoMapper;
import com.lijin.mylab.dao.mybatis.model.StockInfo;
import com.lijin.mylab.dao.mybatis.model.StockPrice;
import com.lijin.mylab.utils.FileLineHandler;
import com.lijin.mylab.utils.FileUtil;
import com.lijin.mylab.utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StockServiceTest {
	
	@Autowired
	private StockInfoMapper stockInfoMapper;
	
	@Test
	public void test() {
	}
	
	@Test
	public void loadData() {
		File dir = new File("/Users/xujin/Develop/mylab/stock_price");
		final List<StockInfo> stockLst = new ArrayList<StockInfo>();
		for (File f : dir.listFiles()) {
			final List<StockPrice> lst = new ArrayList<StockPrice>();
			String fileNm = f.getName();
			if (!fileNm.startsWith("SH") && !fileNm.startsWith("SZ")) {
				continue;
			}
			final String stockNo = fileNm.substring(0, 2).toLowerCase() + fileNm.substring(3, 9);
			FileUtil.readFileByLine(f, "GBK", new FileLineHandler() {
				@Override
				public void handleLine(String line, int lineNo) {
					if (lineNo == 1) {
						String[] strs = line.split(" ");
						StockInfo stock = new StockInfo();
						stock.setStockNo(stockNo);
						stock.setStockNm(strs[1]);
						stockLst.add(stock);
					} else if (lineNo > 3 && line.length() > 28 && line.startsWith("201")) {
						String[] strs = line.substring(0, 28).split("\t");
						if (strs.length == 5) {
							StockPrice entity = new StockPrice();
							entity.setStockNo(stockNo);
							entity.setPriceDt(strs[0].trim());
							entity.setOpenPrice(StringUtil.strToAmt(strs[1].trim()).intValue());
							entity.setMaxPrice(StringUtil.strToAmt(strs[2].trim()).intValue());
							entity.setMinPrice(StringUtil.strToAmt(strs[3].trim()).intValue());
							entity.setClosePrice(StringUtil.strToAmt(strs[4].trim()).intValue());
							lst.add(entity);
						} else {
							System.out.println("illegal price line:" + line);
						}
					}
				}
			});
		}
		stockInfoMapper.batchAdd(stockLst);
	}
}
