package com.lijin.mylab.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;



/**
 * sokcet客户端 
 * 
 * 
 */
public class SocketClient {

	private final static Logger log = Logger
			.getLogger(SocketClient.class);
	private static AtomicInteger num = new AtomicInteger(0);
	private int connectTimeOut = 5000;
	private int readTimeOut = 60000;
	private int soLinger = 0;
	//默认通讯编码
	private String charset = "UTF-8";
	
	private SockectReader reader = new DefaultReader();
	

	/**
	 * JAVA old IO 实现同步短连接
	 * @throws IOException 
	 */
	public byte[] send(byte[] msg, String ip, int port) throws IOException{
		Socket socket = new Socket();
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try {
			num.incrementAndGet();
			//log.info("Socket num["+num.get()+"]["+ip+":"+port+"]"+
			//		" send msg["+ new String(msg,charset)+"]");
			socket.setSoLinger(soLinger >= 0 ? true : false, soLinger);
			socket.connect(new InetSocketAddress(ip, port), connectTimeOut);
			socket.setSoTimeout(readTimeOut);
			out = new BufferedOutputStream(socket.getOutputStream());
			in = new BufferedInputStream(socket.getInputStream());
			out.write(msg);
			out.flush();

			byte[] rlt = reader.read(in);
			//log.info("Socket num["+num.get()+"] receive msg["+new String(rlt,charset)+ "]");
			return rlt;

		} catch (Exception e) {
			log.error("Socket num["+num.get()+"] catch Exception", e);
			throw new IOException(e);
		} finally {
			try {
				num.decrementAndGet();
				if(out != null){
					out.close();
				}
				if(in != null){
					in.close();
				}
				socket.close();
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	
	/**
	 * JAVA old IO 实现同步短连接
	 * 
	 * @throws UnsupportedEncodingException 
	 */
	public String send(String msg, String ip, int port) throws IOException, UnsupportedEncodingException {

		return new String(send(msg.getBytes(charset), ip, port),charset);
	}



	/**
	 * @param connectTimeOut
	 *            the connectTimeOut to set
	 */
	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	/**
	 * @param readTimeOut
	 *            the readTimeOut to set
	 */
	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}



	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	
	
	/**
	 * @param reader the reader to set
	 */
	public void setReader(SockectReader reader) {
		this.reader = reader;
	}



	private static class DefaultReader implements SockectReader{

		@Override
		public byte[] read(BufferedInputStream in) throws Exception {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			do {
				int b = in.read();
				if (b == -1) {
					break;
				}
				buffer.write(b);
			} while (in.available() > 0);
			byte[] rlt = buffer.toByteArray();
			return rlt;
		}
		
	}
}
