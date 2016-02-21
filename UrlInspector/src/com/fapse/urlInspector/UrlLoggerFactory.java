package com.fapse.urlInspector;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UrlLoggerFactory {
	public static Logger getUrlLogger() {
		return createUrlLogger();
	}
	
	private static Logger createUrlLogger() {
		final Logger logger = 
				Logger.getLogger("");		FileHandler fileHandler = null;
		LogManager.getLogManager().reset();
		String logFilePath = "%h" + File.separator
				+ "UrlModel" + File.separator + "logs" + File.separator +
				"UrlInspectorLog.%u.%g.txt";
		try {
			fileHandler = new FileHandler(logFilePath, 1024 * 1024, 5);
		} catch (SecurityException e) {
			System.out.println("Could not set up logging");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Could not set up logging");
			e.printStackTrace();
			System.exit(1);
		}
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		fileHandler.setLevel(Level.FINEST);
		return logger;
	}
}
