package com.fapse.urlInspector.view;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import com.fapse.urlInspector.enrichableException.EnrichableException;

import javafx.scene.control.Alert;

public class ExceptionAlert extends Alert {

	public ExceptionAlert(EnrichableException e) {
		super(AlertType.WARNING);
		String errorMessage = "";
		if (e.getCause() instanceof MalformedURLException) {
			errorMessage = "URL in ungültigem Format eingegeben";
		} else if (e.getCause() instanceof UnknownHostException) {
			errorMessage = "URL konnte nicht gefunden werden";
		} else {
			errorMessage = "Neue Fehlermeldung: " + e.getCause();
		}
		setTitle("Fehler");
		setHeaderText(errorMessage);
		setContentText("Bitte neue URL eingeben");
		showAndWait();
	}
}
