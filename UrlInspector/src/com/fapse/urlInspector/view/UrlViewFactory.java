package com.fapse.urlInspector.view;

import java.io.InputStream;

import com.fapse.urlInspector.Main;
import com.fapse.urlInspector.model.UrlModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UrlViewFactory {

	public static Stage getUrlView(Stage stage, UrlModel urlModel) throws Exception {
		return createUrlView(stage, urlModel);
	}
	
	private static Stage createUrlView(Stage stage, UrlModel urlModel) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/UrlMainView.fxml"));
		BorderPane borderPane = (BorderPane) loader.load();
		UrlMainViewController urlController = loader.getController();
		//urlController.setUrlModel(urlModel);
		//urlController.setLinkList(urlModel.getUrls());
		
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/UrlWorkArea.fxml"));
		VBox workArea = (VBox) loader.load();
		UrlWorkAreaController urlWorkAreaController = loader.getController();
		urlWorkAreaController.setUrlModel(urlModel);
		urlWorkAreaController.setLinkList(urlModel.getUrls());
		
		borderPane.setCenter(workArea);
		
		
		stage.setScene(new Scene(borderPane));
		stage.setTitle("URL Inspector");
		stage.show();
		return stage;
	}
}