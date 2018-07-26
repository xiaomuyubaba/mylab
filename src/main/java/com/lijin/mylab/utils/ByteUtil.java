package com.lijin.mylab.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.codec.binary.Base64;

import com.lijin.mylab.constants.Constant;
import com.lijin.mylab.exception.BizzException;

public class ByteUtil {

	public static byte[] base64Decode(String raw, String charSet) throws IOException {
		if (raw == null || raw.length() == 0) {
			throw new IllegalArgumentException(
					"Decode and inflate error with null raw byte[]");
		}
		if (StringUtil.isBlank(charSet)) {
			charSet = Constant.UTF8;
		}
		return Base64.decodeBase64(raw.getBytes(charSet));
	}

	public static byte[] inflater(byte[] inputByte) throws IOException {
		int compressedDataLength = 0;
		Inflater compresser = new Inflater(false);
		compresser.setInput(inputByte, 0, inputByte.length);
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.inflate(result);
				if (compressedDataLength == 0) {
					break;
				}
				o.write(result, 0, compressedDataLength);
			}
		} catch (DataFormatException e) {
			throw new IOException("decompress catch data format exception", e);
		} finally {
			o.close();
		}
		compresser.end();
		return o.toByteArray();
	}
	

	/**
	 * Descriptor： 压缩直字节数组
	 * @param inputByte
	 * @return byte[]
	 */
	public static byte[] deflater(byte[] inputByte) throws IOException {
		int compressedDataLength = 0;
		Deflater compresser = new Deflater();
		compresser.setInput(inputByte);
		compresser.finish();
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.deflate(result);
				o.write(result, 0, compressedDataLength);
			}
		} finally {
			o.close();
		}
		compresser.end();
		return o.toByteArray();
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public static void unzip(File zipFile, String unzipDirPath) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			ZipFile zip = new ZipFile(zipFile);
			File unzipDir = new File(unzipDirPath);
			if (unzipDir.isDirectory() && unzipDir.exists()) {
				unzipDir.delete();
			}
			unzipDir.mkdirs();
			File fout = null;
			ZipEntry entry = null;
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.entries();
			while(entries.hasMoreElements()) {
				entry = entries.nextElement();
				fout = new File(unzipDir, entry.getName());
				if (fout.isFile() && fout.exists()) {
					fout.delete();
				}
				bis = new BufferedInputStream(zip.getInputStream(entry));
				bos = new BufferedOutputStream(new FileOutputStream(fout));
				int b;
				while ((b = bis.read()) != -1) {
					bos.write(b);
				}
				IOUtil.close(bis);
				IOUtil.close(bos);
			}
		} catch (Throwable t) {
			throw new BizzException("unzip failed:" + zipFile);
		} finally {
			IOUtil.close(bis);
			IOUtil.close(bos);
		}
	}
}
