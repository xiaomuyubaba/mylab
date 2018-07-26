package com.lijin.mylab.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.lijin.mylab.exception.BizzException;

public final class ResourceUtil {

	public static InputStream getResourceAsStream(String resource, ClassLoader loader) {

		InputStream in = null;

		if (loader != null) {
			in = loader.getResourceAsStream(resource);
		} else {
			in = ClassLoader.getSystemResourceAsStream(resource);
		}

		return in;
	}

	public static String getResourceAsString(String resource, ClassLoader loader) throws IOException {
		InputStream in = null;
		InputStreamReader reader = null;
		try {
			in = getResourceAsStream(resource, loader);
			reader = new InputStreamReader(in);
			StringBuffer buffer = new StringBuffer();
			char[] c = new char[1024];
			int size;
			while ((size = reader.read(c)) != -1) {
				buffer.append(c, 0, size);
			}
			return buffer.toString();
		} finally {
			IOUtil.close(reader);
			IOUtil.close(in);
		}
	}

	public static Properties getResourceAsProperty(String resource, ClassLoader loader) {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = getResourceAsStream(resource, loader);
			props.load(in);
			return props;
		} catch (IOException e) {
			throw new BizzException("Error load resource as property", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new BizzException("Error closing InputStream for resource: " + resource, e);
				}
			}
		}
	}

	public static Properties getResourceAsProperty(String resource) {
		return getResourceAsProperty(resource, null);
	}

}