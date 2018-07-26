package com.lijin.mylab.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import com.lijin.mylab.exception.BizzException;

public final class IOUtil extends IOUtils {
	
	public static final char FILE_PATH_SEPARATOR = '/';

	public static void close(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				throw new BizzException("Failed to close InputStream", e);
			}
		}
	}

	public static void close(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				throw new BizzException("Failed to close OutputStream", e);
			}
		}
	}

	public static void close(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				throw new BizzException("Failed to close reader", e);
			}
		}
	}
	
	public static void close(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				throw new BizzException("Failed to close writer", e);
			}
		}
	}

	public static void close(ZipFile zipFile) {
		if (zipFile != null) {
			try {
				zipFile.close();
			} catch (IOException e) {
				throw new BizzException("Failed to close zip file", e);
			}
		}
	}
}
