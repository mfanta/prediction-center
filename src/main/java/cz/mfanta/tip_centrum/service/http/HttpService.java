package cz.mfanta.tip_centrum.service.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class HttpService {

	public InputStream getContentAsStream(String url) {
		HttpClient httpClient = initHttpClient();
		InputStream result = null;
		try {
			HttpGet getMethod = new HttpGet(url);
			HttpResponse response = httpClient.execute(getMethod);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = entity.getContent();
			}
		} catch (IOException ioe) {
			log.error(String.format("Unable to get content from URL '%s'.", url), ioe);
		}
		return result;
	}

	private HttpClient initHttpClient() {
		HttpClient client;
		final String proxyHost = System.getProperty("proxy.host");
		if (proxyHost != null) {
			final String proxyPortStr = System.getProperty("proxy.port");
			final Integer proxyPort = proxyPortStr != null ? Integer.parseInt(proxyPortStr) : 80;
			log.info(String.format("Creating HTTP Client using proxy '%s:%s'", proxyHost, proxyPort));
			final HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			final DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			client = HttpClients.custom().setRoutePlanner(routePlanner).build();
		} else {
			log.info("Creating HTTP Client without proxy");
			client = HttpClients.createDefault();
		}
		return client;
	}
}
