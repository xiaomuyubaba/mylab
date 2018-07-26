package com.lijin.mylab.utils;

import java.io.BufferedInputStream;

public interface SockectReader {
	/**
	 * 根据Server返回的信息格式读取并返回字节流
	 * 注意：不用关闭输入流。
	 */
	byte[] read(BufferedInputStream in) throws Exception;
}
