package com.fapse.urlInspector.view;

import com.fapse.urlInspector.enrichableException.EnrichableException;
import com.fapse.urlInspector.model.UrlModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class UrlModelViewController {
	private BooleanProperty invalidUrl = new SimpleBooleanProperty(true);
	private UrlModel urlModel;
	@FXML
	private TextField txtUrl = new TextField();
	@FXML
	private Button btnStart = new Button();
	@FXML
	private ListView<String> listLinks = new ListView<>();
	@FXML
	private void initialize() {
		btnStart.disableProperty().bind(invalidUrl);
		listLinks.getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends String> ov, String o, String n) -> {
				if (n != null) {
					txtUrl.setText(n);						
				}
			}
		);
		btnStart.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					handleStart();
				}
			}
		});
		listLinks.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					handleStart();
				}
			}
		});
		listLinks.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					String selectedLink = listLinks.getSelectionModel().getSelectedItem();
					if (selectedLink != null) {
						txtUrl.setText(selectedLink);
						handleStart();
					}
				}
			}
		});
	}
	
	@FXML
	private void handleStart() {
		if(!invalidUrl.get()) {
			expandUrlInput();
			try {
				urlModel.inspectUrl(txtUrl.getText());
			} catch (EnrichableException e) {
				new ExceptionAlert(e);
			}
		}
	}
		
	private void expandUrlInput() {
		StringBuilder urlInput = new StringBuilder(txtUrl.getText());
		if (urlInput.length() > 0 && urlInput.indexOf("http://") != 0) {
			urlInput.insert(0, "http://");
			txtUrl.setText(urlInput.toString());
		}
	}

	@FXML
	private void validateUrlInput() {
		// Regex pattern for matching URLs by John Gruber,
		// found on http://daringfireball.net/2010/07/improved_regex_for_matching_urls
		if (txtUrl.getText() != null && txtUrl.getText().matches("(?i)\\b((?:https?://|"
				+ "www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+"
				+ "|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|"
				+ "[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))")) {
			invalidUrl.set(false);
		} else {
			invalidUrl.set(true);
		}
	}
	
	protected void setUrlModel(UrlModel urlModel) {
		this.urlModel = urlModel;
	}
	
	protected void setLinkList(ReadOnlyListWrapper<String> readOnlyListWrapper) {
		readOnlyListWrapper.addListener(new ChangeListener<ObservableList<String>>() {
			@Override
			public void changed(ObservableValue<? extends ObservableList<String>> observable,
					ObservableList<String> oldValue, ObservableList<String> newValue) {
				if(!newValue.isEmpty()) {
					listLinks.setItems(readOnlyListWrapper);
					listLinks.scrollTo(0);
				}
			}
		});
	}
}
