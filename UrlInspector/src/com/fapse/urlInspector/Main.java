package com.fapse.urlInspector;

import java.io.IOException;

import com.fapse.urlInspector.model.UrlModel;
import com.fapse.urlInspector.model.UrlModelFactory;
import com.fapse.urlInspector.view.UrlViewFactory;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		UrlModel urlModel = UrlModelFactory.getUrlModel();
		UrlViewFactory.getUrlView(primaryStage, urlModel);
	}
	
	public static void main(String[] args) throws IOException {
		UrlLoggerFactory.getUrlLogger();
		launch(args);
	}
}