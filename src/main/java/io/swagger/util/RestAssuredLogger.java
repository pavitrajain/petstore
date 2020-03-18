package io.swagger.util;

import com.relevantcodes.extentreports.LogStatus;
import io.swagger.extent_report.ExtentTestManager;
import org.slf4j.Logger;

import java.io.OutputStream;
import java.io.PrintStream;

import static java.util.Objects.isNull;

public class RestAssuredLogger {
	
	private Logger rsLogger;
	private PrintStream rsPrintStream;
	
	public RestAssuredLogger(Logger logger) {
		rsLogger = logger;
	}
	
	public PrintStream getPrintStream() {
		if(isNull(rsPrintStream)) {
			OutputStream outStream = new OutputStream() {
				StringBuilder stringBuilder = new StringBuilder();
				
				@Override
				public void write(int b) {
					this.stringBuilder.append((char) b);
				}
				
				@Override
				public void flush() {
					String trim = this.stringBuilder.toString().trim();
					if(trim.length()>1) {
						rsLogger.info(trim);
						String message = trim.replace("\n", "<br />");
						ExtentTestManager.getTest().log(LogStatus.INFO, message);
					}
					stringBuilder = new StringBuilder();
				}
			};
			rsPrintStream = new PrintStream(outStream, true);
		}
		return rsPrintStream;
	}
}
