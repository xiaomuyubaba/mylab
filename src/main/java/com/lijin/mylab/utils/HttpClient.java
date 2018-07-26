
package com.lijin.mylab.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;


public class HttpClient {
	private static Logger log = Logger.getLogger(HttpClient.class);

	public static final String CR_LF = "\r\n";
	
	private static AtomicInteger activeNum = new AtomicInteger(0);
	
	private String logHead;

	private Socket socket;

	private OutputStream out;

	private InputStream in;

	private String resCode;
	//默认通讯编码
	private String charset = "UTF-8";

	private String resHeader;

	private String resBodyContent;

	private byte[] resBody;

	private StringBuilder resBuffer = new StringBuilder();

	private int socketTimeout = 30000;

	private int connectTimeout = 5000;
	
	private int soLinger = 0;

	private KeyManager[] keyMgrs;

	private X509TrustManager[] trustMgrs;
	
	private String urlStr;

	private URL url;
	
	private boolean logEnabled = true;

	/**
	 * 
	 * 构造http, 或无需认证的https连接
	 */
	public HttpClient(String urlStr){
		this(urlStr, null, null);
	}

	
	/**
	 * 
	 * 构造认证的https连接
	 */
	public HttpClient(String urlStr, KeyManager[] keyMgrs,
			X509TrustManager[] trustMgrs){
		this.urlStr = urlStr;
		this.trustMgrs = trustMgrs;
		this.keyMgrs = keyMgrs;
	}

	/**
	 * 打开连接
	 */
	public void open() throws OpenFailureException {
		logHead = "INSTANCE["+
				String.valueOf(activeNum.incrementAndGet())+ "] to ["+urlStr+"]";

		try{
			url = new URL(urlStr);
			String protocol = url.getProtocol();
			String host = url.getHost();
			int port = url.getPort();

			if ("https".equalsIgnoreCase(protocol)) {
				port = (port == -1 ? 443 : port);
				openSSL(host,port);
			} else {
				port = (port == -1 ? 80 : port);
				socket = new Socket();
			}
			
			socket.setSoLinger(soLinger >= 0 ? true : false, soLinger);
			socket.connect(new InetSocketAddress(host, port), connectTimeout);
			socket.setSoTimeout(socketTimeout);
			out = new BufferedOutputStream(socket.getOutputStream());
			in = new BufferedInputStream(socket.getInputStream());
		}catch(Exception e){
			throw new OpenFailureException("open connection to["+urlStr+"] failed", e);
		}

		
		//log.info(concat(logHead, urlStr," connected."));
	}

	private void openSSL(String host, int port) throws NoSuchAlgorithmException, KeyManagementException, IOException {
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(keyMgrs,
						((trustMgrs == null || trustMgrs.length < 1) ? new X509TrustManager[] { new DefaultTrustManager() }
								: trustMgrs), null);
		SSLSocketFactory factory = sslContext.getSocketFactory();
		socket = factory.createSocket();
	}

	private void request(byte[] contents) throws IOException {
		long begin = System.currentTimeMillis();
		out.write(contents);
		out.flush();

		/** Receive Response **/
		String statusLine = new String(readRawLine());
		resBuffer.append(statusLine);
		//int statusIndex = statusLine.indexOf("HTTP/1.1");
		if (statusLine.startsWith("HTTP/1.")) {
			resCode = statusLine.substring(9, 12);
		}

		/** Read http header **/
		StringBuilder headerBuffer = new StringBuilder();
		int bodyLength = -1;
		while (true) {
			String buffer = readLine();
			headerBuffer.append(buffer);
			headerBuffer.append(CR_LF);
			if (isEmpty(buffer)) {
				break;
			}
			int headerIndex = buffer.indexOf("Content-Length");
			if (headerIndex == -1) {
				headerIndex = buffer.indexOf("Content-length");
			}

			if (headerIndex > -1) {
				bodyLength = Integer.parseInt(buffer
						.substring(headerIndex + 16));
			}
		}
		resHeader = headerBuffer.toString();
		resBuffer.append(resHeader);

		/** Read http body **/
		if (bodyLength != -1) {
			resBody = read(bodyLength);
		} else {
			/** 根据Transfer-Encoding: chunked读取body **/
			ByteArrayOutputStream bodyBuffer = new ByteArrayOutputStream();
			while (true) {
				String buffer = readLine();
				if (isEmpty(buffer)) {// ignore /r/n line
					buffer = readLine();
				}
				if (!isEmpty(buffer)) {
					int length = Integer.parseInt(buffer, 16);
					if (length > 0) {
						bodyBuffer.write(read(length));
					} else {
						break;
					}
				} else {
					break;
				}
			}

			resBody = bodyBuffer.toByteArray();
		}

		if (resBody != null) {
			resBuffer.append(new String(resBody, charset));
		}
		if(logEnabled && log.isInfoEnabled()){
			log.info(concat(logHead, "cost "+(System.currentTimeMillis()-begin)+" ms\n",
					resBuffer.toString()));
		}	
	}

	private String readLine() throws IOException {
		byte[] rawLine = readRawLine();
		int i = rawLine.length;
		int j = 0;
		if (i > 0 && rawLine[i - 1] == '\n') {
			j++;
			if (i > 1 && rawLine[i - 2] == '\r')
				j++;
		}
		return new String(rawLine, 0, i - j, charset);
	}

	private byte[] readRawLine() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int i = -1;
		do {
			if ((i = in.read()) < 0) {
				break;
			}

			buffer.write(i);
		} while (i != '\n');

		return buffer.toByteArray();
	}

	private byte[] read(int length) throws IOException {
		if(length<=0){
			return new byte[0];
		}
		ByteArrayOutputStream buffer = new ByteArrayOutputStream(length);
		int i = -1, j = 0;
		do {
			if ((i = in.read()) < 0) {
				break;
			}

			buffer.write(i);
		} while (++j < length);

		return buffer.toByteArray();
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @return the resCode
	 */
	public String getResCode() {
		return resCode;
	}

	/**
	 * @return the resHeader
	 */
	public String getResHeader() {
		return resHeader;
	}

	/**
	 * @return the resBody
	 */
	public byte[] getResBodyInByte() {
		return resBody;
	}
	

	public void close() {
		activeNum.decrementAndGet();
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				log.error("close meet excetption",  e);//(Level.WARNING, , e);
			}
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				log.error("close meet excetption", e);
			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				log.error("close meet excetption", e);
			}
		}
		//log.info(concat(logHead,"closed."));
	}

	public String getResBody() throws UnsupportedEncodingException {
		if(resBodyContent==null){
			decodeResBody();
		}
		return resBodyContent;
	}

	/**
	 * http post
	 */
	public void httpPost(Map<String, String> postData)
			throws UnsupportedEncodingException, IOException {
		httpPost(postData, null);
	}

	/**
	 * http get
	 */
	public void httpGet()
			throws UnsupportedEncodingException, IOException {
		String head = composeHeader(0, null,
				HttpMethod.GET);

		if(logEnabled && log.isInfoEnabled()){
			log.info(concat(logHead, "\r\n", head));
		}	
		request(head.getBytes(charset));
	}

	/**
	 * http post
	 */
	public void httpPost(String body)
			throws UnsupportedEncodingException, IOException {
		String head = composeHeader(body.getBytes(charset).length, null,
				HttpMethod.POST);
		String httpMsg = concat(head, body);
		if(logEnabled && log.isInfoEnabled()){
		log.info(concat(logHead, "\r\n", httpMsg));
		}
		request(httpMsg.getBytes(charset));
	}
	
	public void httpPost(byte[] body)
			throws IOException {
		byte[] head = composeHeader(body.length, null,
				HttpMethod.POST).getBytes(charset);

		byte[] httpMsg = new byte[head.length+body.length]; 
		System.arraycopy(head, 0, httpMsg, 0, head.length);
		System.arraycopy(body, 0, httpMsg, head.length, body.length);
		request(httpMsg);
	}

	/**
	 * http post
	 */
	public void httpPost(Map<String, String> postData,
			Map<String, String> headData)
			throws UnsupportedEncodingException, IOException {
		String body = getPostBody(postData);
		String head = composeHeader(body.getBytes(charset).length, headData,
				HttpMethod.POST);
		String httpMsg = concat(head, body);
		if(logEnabled && log.isInfoEnabled()){
			log.info(concat(logHead, "\r\n", concat(head, formatMap(postData))));
		}		
		request(httpMsg.getBytes(charset));
	}
	
	private String formatMap(Map<String, String> postData){
		Iterator<String> iterator = postData.keySet().iterator();
		StringBuilder postBody = new StringBuilder();
		while (iterator.hasNext()) {
			String key = iterator.next();
			postBody.append(key).append("=")
					.append(postData.get(key));
			if (iterator.hasNext()) {
				postBody.append("&");
			}
		}
		return postBody.toString();
	}

	private String getPostBody(Map<String, String> postData)
			throws UnsupportedEncodingException {
		Iterator<String> iterator = postData.keySet().iterator();
		StringBuffer postBody = new StringBuffer();
		while (iterator.hasNext()) {
			String key = iterator.next();
			postBody.append(URLEncoder.encode(key, charset)).append("=")
					.append(URLEncoder.encode(postData.get(key), charset));
			if (iterator.hasNext()) {
				postBody.append("&");
			}
		}
		return postBody.toString();
	}

	private void decodeResBody() throws UnsupportedEncodingException{
		if (resBody != null) {
			if (resHeader.indexOf("application/x-www-form-urlencoded") != -1) {
				resBodyContent = URLDecoder.decode(
						new String(resBody, charset), charset);
			} else {
				resBodyContent = new String(resBody, charset);
			}

		}
	}

	private String composeHeader(int bodyLength, Map<String, String> header,
			HttpMethod method) {
		Map<String, String> defaultHeader = new LinkedHashMap<String, String>();
		defaultHeader.put("User-Agent: ", "Java/1.6.0");
		defaultHeader.put("Host: ", url.getHost());
		defaultHeader.put("Content-Length: ", String.valueOf(bodyLength));
		defaultHeader
				.put("Content-Type: ", "application/x-www-form-urlencoded");

		if (header != null) {
			defaultHeader.putAll(header);
		}

		String path = url.getPath();
		if(HttpMethod.GET.equals(method)){
			path = path +"?"+url.getQuery();
		}

		StringBuilder headerMsg = new StringBuilder(method.getValue())
				.append(" ").append(path).append(" HTTP/1.1").append(CR_LF);
		for (String key : defaultHeader.keySet()) {
			headerMsg.append(key).append(defaultHeader.get(key)).append(CR_LF);
		}
		headerMsg.append(CR_LF);
		return headerMsg.toString();
	}

	public static class DefaultTrustManager implements X509TrustManager {
		DefaultTrustManager() {
		}

		public void checkClientTrusted(X509Certificate chain[], String authType)
				throws CertificateException {

		}

		public void checkServerTrusted(X509Certificate chain[], String authType)
				throws CertificateException {

		}

		public X509Certificate[] getAcceptedIssuers() {

			return null;
		}
	}

	public enum HttpMethod {
		GET("GET"), POST("POST");
		private String value;

		HttpMethod(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	private static String concat(String... pieces) {
		StringBuffer sb = new StringBuffer();
		for (String piece : pieces) {
			sb.append(piece);
		}
		return sb.toString();
	}

	/**
	 * Checks if string is null or empty.
	 * 
	 * @param value
	 *            The string to be checked
	 * @return True if string is null or empty, otherwise false.
	 */
	private static boolean isEmpty(final String value) {
		return value == null || value.trim().length() == 0;
	}


	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}


	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}


	/**
	 * @return the socketTimeout
	 */
	public int getSocketTimeout() {
		return socketTimeout;
	}


	/**
	 * @param socketTimeout the socketTimeout to set
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}


	/**
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}


	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}


	/**
	 * @return the soLinger
	 */
	public int getSoLinger() {
		return soLinger;
	}


	/**
	 * @param soLinger the soLinger to set
	 */
	public void setSoLinger(int soLinger) {
		this.soLinger = soLinger;
	}
	
	


	
	/**
	 * @param logEnabled the logEnabled to set
	 */
	public void setLogEnabled(boolean logEnabled) {
		this.logEnabled = logEnabled;
	}

	public static class OpenFailureException extends IOException {

		private static final long serialVersionUID = 1L;

		public OpenFailureException(String message) {
			super(message);
		}

		public OpenFailureException(String message, Throwable cause) {
			super(message,cause);
		}

		public OpenFailureException(Throwable cause) {
			super(cause);
		}
	}
	

	public static void main(String[] args){

		HttpClient client = new HttpClient("https://www.baidu.com?c=index&a=login");

		try {
			client.open();
			client.httpGet();
			System.out.println(client.getResCode()+"XXX");
			System.out.println(client.getResHeader()+"XXX");
			System.out.println(client.getResBody()+"XXX");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

				client.close();

		}
	}
}
