package com.lijin.mylab.constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Constant {

	public static final String UTF8 = "UTF-8";
	public static final String GBK = "GBK";
	
	public static final String RISK_RULE_PREFIX = "rule.";

	public static final String CURR_CN = "156"; // 人民币币种
	
	public static final byte[] KEY = {0x69,0x63,0x70,0x61,0x79,(byte)0xe6,(byte)0x94,(byte)0xb6,
		(byte)0xe5,(byte)0x8d,(byte)0x95,(byte)0xe5,(byte)0xb9,(byte)0xb3,(byte)0xe5,(byte)0x8f};

	public static final class SIGNMETHOD {	
		public static final  String _01 = "01"; //RSA
		public static final  String _02 = "02"; //SHA-256
	}
	
	public static final class MSG {
		public static final String version         ="version";//版本号        
		public static final String sign_method     ="sign_method";//签名方法        
		public static final String sign            ="sign";//签名      
		public static final String trans_code         ="trans_code";//交易类型      
		public static final String mer_code           ="mer_code";//商户代码      
		public static final String order_no         ="order_no";//商户订单号    
		public static final String txn_date         ="txn_date";//订单发送时间  
		public static final String amount          ="amount";//交易金额      
		public static final String currency    ="currency";//交易币种       
		public static final String card_no           ="card_no";//卡号          
		public static final String bank_code          ="bank_code";//银行简码     
		public static final String name      ="name";//姓名         
		public static final String id_type        ="id_type";//证件类型   
		public static final String id_no        ="id_no";//证件号码
		public static final String phone         ="phone";//手机号
		public static final String cvn2            ="cvn2";//CVN2
		public static final String expired         ="expired";//有效期  
		public static final String resp_code        ="resp_code";//响应码        
		public static final String resp_msg         ="resp_msg";//响应码描述    
		public static final String stlm_date      ="stlm_date";//清算日期      
		public static final String org_order_no         ="org_order_no";//商户订单号    
		public static final String org_txn_date         ="org_txn_date";//订单发送时间  
		
		public static final String org_resp_code    ="org_resp_code";//原交易应答码
		public static final String org_resp_msg     ="org_resp_msg";//原交易应答信息 
		public static final String sms_code     =  "sms_code";//短信验证码 
		public static final String front_url         ="front_url";//后台通知地址
		public static final String back_url         ="back_url";//前台通知地址
		public static final String mer_addmsg         ="mer_addmsg";//前台通知地址
		public static final String encrypted_pin         ="encrypted_pin";//密码
		public static final String file_date         ="file_date";//文件日期
		public static final String file_type         ="file_type";//文件类型
		public static final String file_name         ="file_name";//文件名
		public static final String file_content      ="file_content";//文件内容
		public static final String ic_data           ="ic_data";//IC卡数据域
		public static final String ic_seqnum      ="ic_seqnum";//IC卡的序列号
		public static final String track2_data      ="track2_data";//磁条卡第二磁数据
		public static final String track3_data      ="track3_data";//磁条卡第三磁数据
		public static final String pos_entrycode      ="pos_entrycode";//服务点输入方式码
		public static final String term_cap          ="term_cap";//终端读取能力
		public static final String chip_code          ="chip_code";//IC卡条件代码
		public static final String snd_mode          ="snd_mode";//交易发起方式
		public static final String term_id          ="term_id";//终端号
		public static final String remarks          ="remarks";//附言
		public static final String sms_type        ="sms_type";//短信类型   
		public static final String available_balance        ="available_balance";//账户可用余额   
	
	}
	
	public static final class UP_MSG {
		public static final String version         ="version";//版本号        
		public static final String signMethod      ="signMethod";//签名方法    
		public static final String certId          ="certId";//证书ID      
		public static final String signature       ="signature";//签名      
		public static final String txnType         ="txnType";//交易类型      
		public static final String txnSubType      ="txnSubType";//交易子类      
		public static final String merId           ="merId";//商户代码      
		public static final String orderId         ="orderId";//商户订单号    
		public static final String txnTime         ="txnTime";//订单发送时间  
		public static final String txnAmt          ="txnAmt";//交易金额      
		public static final String currencyCode    ="currencyCode";//交易币种       
		public static final String accType         ="accType";//卡类型        
		public static final String accNo           ="accNo";//卡号          
		public static final String customerNm      ="customerNm";//姓名         
		public static final String certifTp        ="certifTp";//证件类型   
		public static final String certifId        ="certifId";//证件号码
		public static final String phoneNo         ="phoneNo";//手机号
		public static final String pin             ="pin";//持卡人密码
		public static final String cvn2            ="cvn2";//CVN2
		public static final String expired         ="expired";//有效期  
		public static final String respCode        ="respCode";//响应码        
		public static final String respMsg         ="respMsg";//响应码描述    
		public static final String queryId         ="queryId";//交易查询流水号
		public static final String settleDate      ="settleDate";//清算日期      
		public static final String traceNo         ="traceNo";//系统跟踪号    
		public static final String origQryId       ="origQryId";//原始交易流水号	
		public static final String extData         ="extData";//个性化信息    
		public static final String origRespCode    ="origRespCode";//原交易应答码
		public static final String origRespMsg     ="origRespMsg";//原交易应答信息 
		public static final String smsCode     ="smsCode";//短信验证码 
		public static final String encoding         ="encoding";//编码方式
		public static final String channelType         ="channelType";//渠道类型    
		public static final String accessType         ="accessType";//接入类型    
		public static final String bizType         ="bizType";//产品类型
		public static final String customerInfo         ="customerInfo";//银行卡验证信息及身份信息		public static final String backUrl         ="backUrl";//后台通知地址
		public static final String backUrl         ="backUrl";//后台通知地址
		public static final String frontUrl         ="frontUrl";//前台通知地址
		public static final String issInsCode         ="issInsCode";//发卡机构代码
		public static final String fileType         ="fileType";//文件类型
		public static final String fileName = "fileName";
		public static final String fileContent = "fileContent";
		public static final String encryptCertId = "encryptCertId";
		public static final String encryptedInfo = "encryptedInfo";
		public static final String activateStatus = "activateStatus";
		public static final String cardTransData = "cardTransData";
		
		public static final String ICCardData = "ICCardData";
		public static final String ICCardSeqNumber = "ICCardSeqNumber";
		public static final String track2Data = "track2Data";
		public static final String track3Data = "track3Data";
		public static final String POSentryModeCode = "POSentryModeCode";
		public static final String termEntryCap = "termEntryCap";
		public static final String chipCondCode = "chipCondCode";
		public static final String transSendMode = "transSendMode";
		public static final String carrierAppTp = "carrierAppTp";
		public static final String carrierTp = "carrierTp";
		public static final String termId = "termId";
		
		public static final String reserved = "reserved";
		public static final String checkFlag = "checkFlag";
		public static final String billNo = "billNo";
		public static final String signPubKeyCert = "signPubKeyCert";
		public static final String customerIp = "customerIp";
		
		
		
		
		
		
		
	}
	
	public static final class INTER_MSG {
		public static final String origQryId = "origQryId";
		public static final String queryId ="queryId";//交易查询流水号
		//交易相关
		public static final String oId = "oId";
		public static final String reqIp = "reqIp";
		public static final String referer = "referer";
		public static final String payType = "payType";
		public static final String sessionId = "sessionId";
		public static final String orderState = "orderState";
		public static final String ctrlMsg = "ctrlMsg";
		public static final String channel = "channel";
		public static final String chnlRespCd = "chnlRespCd";//
		public static final String chnlSettleCurrencyCode = "chnlSettleCurrencyCode";// 清算币种-AN3
		public static final String chnlSsettleCurrencyExp = "chnlSsettleCurrencyExp";// 清算货币指数-N1
		public static final String chnlExchangeRate = "chnlExchangeRate";// 清算汇率-N8
		public static final String chnlSettleAmt = "chnlSettleAmt";// 清算金额-N1..12
		public static final String chnlSettleDate = "chnlSettleDate";// 清算日期-YYYYMMDD
		public static final String chnlTraceNo = "traceNo";// 系统跟踪号-N6
		public static final String chnlTraceTime = "traceTime";// 交易传输时间-YYYYMMDDhhmmss
		public static final String chnlQueryId = "chnlQueryId";// 查询流水号-AN21
		public static final String origChnlQueryId = "origChnlQueryId";// 查询流水号-AN21
		public static final String origChnlBatchNo = "origChnlBatchNo";// 原交易的批次号-N6
		public static final String origChnlTraceNo = "origChnlTraceNo";// 原交易的流水号-N6
		public static final String origTxnType = "origTxnType";// 交易类型-N2
//		public static final String origTxnSubType = "origTxnSubType";// 交易子类-N2
		public static final String origTxnTime         ="origTxnTime";//原交易订单发送时间  
		public static final String origTraceTime         ="origTraceTime";//原交易传输时间-YYYYMMDDhhmmss
		public static final String acqInsCode = "acqInsCode";// 收单机构代码
		public static final String agentCode = "agentCode";// 代理商代码
		public static final String userId = "userId";
		public static final String forwardUrl = "forwardUrl";
		public static final String cardType = "cardType";
		public static final String issCode = "issCode";
		public static final String fileType = "fileType";
		public static final String fileName = "fileName";
		public static final String fileContent = "fileContent";
		
		
		public static final String chnlTermId = "chnlTermId";// 渠道虚拟终端号
		public static final String chnlMchntId = "chnlMchntId";// 渠道商户号
		public static final String chnlTxnType = "chnlTxnType";// 渠道交易类型
		public static final String operId = "operId";// 操作员
		
		//虚拟账户相关
		public static final String accOperType = "accOperType";
		public static final String note = "note";
		
		//清算相关
		public static final String settleBatch = "settleBatch";
		public static final String isBack = "isBack";

		public static final String batchDtilId ="batchDtilId";
	}
	
	public static final class VIEW {
		public static final String oId = "oId";
		public static final String orderId = "orderId";
		public static final String txnTime = "txnTime";// 交易时间-YYYYMMDDhhmmss
		public static final String merName = "merName";
		public static final String supportPayType = "supportPayType";
		public static final String defaultPayType = "defaultPayType";
		public static final String payType = "payType";
		public static final String txnAmt = "txnAmt";
		public static final String eBankList = "eBankList";
		public static final String eBankSelected = "eBankSelected";
		public static final String respMap = "respMap";
		public static final String postUrl = "postUrl";	
		public static final String userId = "userId";
		public static final String userName = "userName";
		public static final String isMerUser = "isMerUser";
		public static final String avaiableBalance = "avaiableBalance";
		public static final String frozenBalance = "frozenBalance";
		public static final String loginName = "loginName";
		public static final String loginPwd = "loginPwd";
		public static final String payPwd = "payPwd";
		public static final String cardList = "cardList";
		public static final String cardNum = "cardNum";
		public static final String phoneNum = "phoneNum";
		public static final String smsCode = "smsCode";
		public static final String imageCode = "imageCode";
		public static final String result = "result";
		public static final String resultDesc = "resultDesc";
		public static final String merCd = "merCd";
		public static final String txnType = "txnType";// 交易类型-N4
		public static final String orderDesc = "orderDesc";
	}
	
	public static final class CERTTYPE {	
		/**02-商户公钥*/
		public static final  String _01= "01";

	}
	

	public static final class ROUTE {
		
		public static final  String _koukuan = "koukuan"; 
		
		public static final  String _yanzheng = "yanzheng"; 
		
		public static final  String _default = "default"; 
		
		public static final  String _chnlId = "chnlId"; 
		public static final  String _chnlMerId = "chnlMerId"; 
		public static final  String _chnlTxnType = "chnlTxnType"; 
		public static final  String _metas = "metas"; 
	}

	
	

	
	public static final class PAYTYPE {	
		/**后台支付*/
		public static final  int _0 = 0; 
		/**个人账户支付*/
		public static final  int _1 = 1; 		
		/**企业账户支付*/
		public static final  int _2 = 2;
		/**网银支付*/
		public static final  int _3 = 3; 
		/**企业网银支付*/
		public static final  int _4 = 4; 
		/**快捷支付*/
		public static final  int _5 = 5;
		public static final String[] name = new String[]{"后台支付","个人账户支付","企业账户支付","网银支付","企业网银支付","快捷支付"};
	}
	
	public static final class ORDER_STATE {
		/**处理中*/
		public static final  String _0 = "0";
		/**成功*/
		public static final  String _1 = "1";
		/**失败*/
		public static final  String _2 = "2"; 
//		/**已撤销*/
//		public static final  String _3 = "3"; 
//		/**已退货*/
//		public static final  String _4 = "4"; 
//		/**已冲正*/
//		public static final  String _5 = "5";
	}
	
	public static final class CHANNEL {	
		/**不支持*/
		public static final  String _00 = "00"; 
		/**银联*/
		public static final  String _01 = "01"; 
		/**国采*/
		public static final  String _02 = "02"; 
		/**恒丰*/
		public static final  String _03 = "03"; 
		/**中信*/
		public static final  String _04 = "04"; 
		/**联动*/
		public static final  String _05 = "05"; 
		/**CP*/
		public static final  String _06 = "06"; 

	}
	
	public static final class RISK_THRESHOLD_TP {
		/**金额*/
		public static final  String _01 = "01"; 
		/**笔数*/
		public static final  String _02 = "02"; 
	}
	
	
	public static final class LOGIN_STATE {	
		/**登录成功*/
		public static final  int _0 = 0; 
		/**登陆失败*/
		public static final  int _1 = 1; 
		/**失败，用户锁定*/
		public static final  int _2 = 2; 
	}
	public static final class RISK_RESULT {	
		public static final  String _0 = "0"; //拒绝
		public static final  String _1 = "1"; //短信报警
		public static final  String _2 = "2"; 
	}
	
	public static final class RISK_ITEM_TP {	
		public static final  String _01 = "01"; //卡号
		public static final  String _02 = "02"; //手机号
		public static final  String _03 = "03"; //用户号
		public static final  String _04 = "04"; //商户号
		public static final  String _05 = "05"; //IP
		public static final  String _06 = "06"; //终端序列号
	}
	
	public static final Map<String, Map<String,String>> CHNL_TXN_TYPE= new HashMap<String, Map<String,String>>();
	static{
//		Map<String,String> chn01 = new LinkedHashMap<String,String>();
//		chn01.put("0101", "消费");
//		chn01.put("0102", "认证支付2.0消费");
//		chn01.put("1100", "代收");
//		chn01.put("7201", "后台实名认证");
//		chn01.put("7210", "前台实名认证");
//		chn01.put("7900", "支付开通");
//		chn01.put("7702", "发短信");
//		chn01.put("0201", "预授权");
//		CHNL_TXN_TYPE.put(CHANNEL._01, chn01);
//		
//		Map<String,String> chn02 = new LinkedHashMap<String,String>();
//		chn02.put("0000", "代收");
//		chn02.put("1000", "余额代付");
//		chn02.put("1001", "垫资代付");
//		CHNL_TXN_TYPE.put(CHANNEL._02, chn02);
//		
//		Map<String,String> chn03 = new LinkedHashMap<String,String>();
//		chn03.put("1010", "代收");
//		chn03.put("1025", "垫资代付");
//		CHNL_TXN_TYPE.put(CHANNEL._03, chn03);
//		
//		Map<String,String> chn04 = new LinkedHashMap<String,String>();
//		chn04.put("DK", "代收");
//		chn04.put("DF", "代付");
//		CHNL_TXN_TYPE.put(CHANNEL._04, chn04);
//		
//		Map<String,String> chn05 = new LinkedHashMap<String,String>();
//		chn05.put("WY", "网银");
//		chn05.put("DF", "代付");
//		CHNL_TXN_TYPE.put(CHANNEL._05, chn05);
		
		Map<String,String> chn06 = new LinkedHashMap<String,String>();
		chn06.put("0003", "代扣");
		CHNL_TXN_TYPE.put(CHANNEL._06, chn06);
	} 
	
	public static final class SETTLE_AlG_KEY {	
		public static final  String fixFee = "fixFee"; //单笔固定手续费（整数 分）
		public static final  String fixRate = "fixRate"; //单笔固定费率  （小数）
		public static final  String rangeFrom = "rangeFrom"; //区间
		public static final  String rangeTo = "rangeTo"; //区间
		public static final  String rate = "rate"; 
		public static final  String maxFee = "maxFee"; //封顶手续费
		public static final  String minFee = "minFee"; //保底手续费
	}
	
	// 数据字典类型
	public static final class DATA_DIC_DATA_TP {
		public static final String MER_RISK_FLG = "MER_RISK_FLG";
		public static final String TRADE_TYPE = "TRADE_TYPE";
	}

	public static final String VALIDATION_CODE_KEY = "VALIDATION_CODE_KEY";
	public static final String SMS_VALIDATE_CODE_KEY = "SMS_VALIDATE_CODE_KEY";
	public static final String SMS_VALIDATE_CODE_TM_KEY = "SMS_VALIDATE_CODE_TM_KEY";
	
	public static final String CHECK_TASK_CTX_KEY_TASK_TP = "TASK_TP";
	public static final String CHECK_TASK_CTX_KEY_OPER = "TASK_OPER";
	public static final String CHECK_TASK_CTX_KEY_SYSID = "TASK_SYS_ID";
	public static final String CHECK_TASK_CTX_KEY_OPTP = "TASK_OPTP";
	public static final String CHECK_TASK_CTX_KEY_CONTENT = "TASK_CONTENT";
	public static final String CHECK_TASK_CTX_KEY_CHEKER = "TASK_CHECKER";
	public static final String CHECK_TASK_CTX_KEY_OPER_COMMENTS = "TASK_OPER_COMMENTS";
	public static final String CHECK_TASK_CTX_KEY_CHEKER_COMMENTS = "TASK_CHECKER_COMMENTS";
	
	public static final String MCHNT_DFT_TXN_TYPES = "0000000000000000000000000000000000000000";
	public static final String MCHNT_DFT_PAY_TYPES = "00000000000000000000";
	public static final String MCHNT_DFT_STCD = "0000000000000000000000000000000000000000";
	public static final String MCHNT_DFT_AUTH_INFO = "00000000000000000000";

	public static final String CHNL_DFT_TXN_TYPES = "0000000000000000000000000000000000000000";
	
	
	/**
	 * 虚拟账户操作类型
	 * 
	 */
	public static final class OPERTYPE {	
		/**收入-人工充值(不参与交易流水勾兑)*/
		public static final  String _00 = "00";
		/**收入-代付交易失败撤销*/
		public static final  String _01 = "01";
		/**收入-差错调增(不参与交易流水勾兑)*/
		public static final  String _02 = "02";
		/**收入-收款交易充值*/
		public static final  String _03 = "03";

		/**支出-代付交易扣除*/
		public static final  String _10 = "10";
		/**支出-服务费扣减(不参与交易流水勾兑)*/
		public static final  String _11 = "11";
		/**支出-差错扣减(不参与交易流水勾兑)*/
		public static final  String _12 = "12";
		/**支出-手工提现(不参与交易流水勾兑)*/
		public static final  String _13 = "13";

	}
}
