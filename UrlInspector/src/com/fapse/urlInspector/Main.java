package com.fapse.urlInspector;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.fapse.urlInspector.model.UrlModel;
import com.fapse.urlInspector.model.UrlModelFactory;
import com.fapse.urlInspector.view.UrlViewFactory;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static final Logger logger =
			Logger.getLogger(UrlModel.class.getName());
	private static FileHandler fileHandler;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		UrlModel urlModel = UrlModelFactory.getUrlModel();
		UrlViewFactory.getUrlView(primaryStage, urlModel);
	}
	
	private static void setupLogging() {
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
	}

	public static void main(String[] args) throws IOException {
		setupLogging();
		launch(args);
	}
}