package com.fapse.urlInspector.view;

import com.fapse.urlInspector.Main;
import com.fapse.urlInspector.model.UrlModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UrlViewFactory {

	public static Stage getUrlView(Stage stage, UrlModel urlModel) throws Exception {
		return createUrlView(stage, urlModel);
	}
	
	private static Stage createUrlView(Stage stage, UrlModel urlModel) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/UrlView.fxml"));
		BorderPane borderPane = (BorderPane) loader.load();
		UrlModelViewController urlController = loader.getController();
		urlController.setUrlModel(urlModel);
		urlController.setLinkList(urlModel.getUrls());
		stage.setScene(new Scene(borderPane));
		stage.setTitle("URL Inspector");
		stage.show();
		return stage;
	}
}