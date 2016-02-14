package com.fapse.urlInspector.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fapse.urlInspector.enrichableException.EnrichableException;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UrlModel {
	private static final Logger LOGGER =
			Logger.getLogger(UrlModel.class.getName());
	
	private ObservableList<String> urls = FXCollections.observableArrayList();
	
	public void inspectUrl(String urlInput) {
		URL url = null;
		try {
			url = new URL(urlInput);
		} catch (MalformedURLException ioexception) {
			LOGGER.warning("Could not create URL from String " + urlInput);
			throw new EnrichableException("UrlModel.inspectUrl(String urlInput)",
					"MalformedURL", "Could not generate Url from String urlInput: " + urlInput,
					ioexception);
		}
		List<String> urlHtmlContent;
		urlHtmlContent = getUrlContent(url);
		List<String> urlHtmlLinks = extractHtmlLinks(urlHtmlContent);
		List<String> externalLinks = filterHtmlLinks(urlHtmlLinks);		
		List<String> urls = extractUrls(externalLinks);
		List<String> sortedUrls = sortUrls(urls);
		this.urls.clear();
		this.urls.addAll(sortedUrls);
	}
	
	public ReadOnlyListWrapper<String> getUrls() {
		return new ReadOnlyListWrapper<>(urls);
	}
	
	private List<String> getUrlContent(URL url) {
		List<String> urlContentLines = new ArrayList<>();
		URLConnection conn;
		try {
			conn = url.openConnection();
		} catch (IOException ioexception) {
			LOGGER.warning("Could not open connection to URL " + url.toString());
			throw new EnrichableException("UrlModel.getUrlContent(URL url)",
					"URLConnection failed", "Could not open connection to url " + url.toString(),
					ioexception);
		}
		BufferedReader br;
		try {
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
		} catch (IOException ioexception) {
			LOGGER.warning("Could not create BufferedReader from URL " + url.toString());
			throw new EnrichableException("UrlModel.getUrlContent(URL url)",
					"URLConnection failed", "Could not get input stream from connection to url " + url.toString(),
					ioexception);
		}
		String inputLine;
		try {
			while ((inputLine = br.readLine()) != null) {
				urlContentLines.add(inputLine);
			}
		} catch (IOException ioexception) {
			LOGGER.warning("Could not read line from BufferedReader");
			throw new EnrichableException("UrlModel.getUrlContent(URL url)",
					"URLConnection failed", "Could not read from connection to url " + url.toString(),
					ioexception);
		}
		return urlContentLines;
	}
	
	private List<String> extractHtmlLinks(List<String> urlContent) {
		List<String> htmlLinks = new ArrayList<>();
		Pattern htmlLinkPattern = Pattern.compile("<a\\b[^>]*href=\"[^>]*>(.*?)</a>");
		for (String contentLine : urlContent) {
			Matcher matcher = htmlLinkPattern.matcher(contentLine);
			while (matcher.find()) {
				htmlLinks.add(matcher.group());
			}
		}
		return htmlLinks;
	}
	
	private List<String> filterHtmlLinks(List<String> htmlLinks) {
		return htmlLinks.stream()
			.filter(p -> p.contains("href=\"http://"))
			.filter(p -> !p.contains("href=\"http://mail"))
			.collect(Collectors.toList());
	}
	
	private List<String> extractUrls(List<String> htmlLinks) {
		List<String> httpLinks = new ArrayList<>();
		for (String htmlLink : htmlLinks) {
			StringBuilder httpLink = new StringBuilder("");
			int start = htmlLink.indexOf("http://");
			httpLink.append(htmlLink.substring(start, htmlLink.indexOf('"', ++start)));
			httpLinks.add(httpLink.toString());
		}
		return httpLinks;
	}
	
	private List<String> sortUrls(List<String> urls) {
		return new TreeSet<String>(urls).stream().collect(Collectors.toList());
	}
}