package com.aiven.log.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class LogPrintWriter extends PrintWriter {

	public LogPrintWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
	}

	public LogPrintWriter(File file) throws FileNotFoundException {
		super(file);
	}

	public LogPrintWriter(OutputStream out) {
		super(out);
	}

	private boolean isCrashWriter = false;

	public boolean isCrashWriter() {
		return isCrashWriter;
	}

	public void setCrashWriter(boolean isCrashWriter) {
		this.isCrashWriter = isCrashWriter;
	}

}
