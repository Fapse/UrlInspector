package com.fapse.urlInspector.model;

public class UrlModelFactory {
	public static UrlModel getUrlModel() {
		return createUrlModel();
	}
	
	private static UrlModel createUrlModel() {
		UrlModel urlModel = new UrlModel();
		return urlModel;
	}
}
