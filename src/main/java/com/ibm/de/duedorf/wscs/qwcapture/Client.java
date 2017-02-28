package com.ibm.de.duedorf.wscs.qwcapture;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

public class Client {
	private static final Logger logger = Logger.getLogger(Client.class.getName());

	public static CloseableHttpClient createClient(HostInfo hi) throws IOException {

		BasicCookieStore sessionIdStore = new BasicCookieStore();

		logger.log(Level.INFO, "Create the HTTP Client Builder");
		HttpClientBuilder clientBuilder = HttpClients.custom().setDefaultCookieStore(sessionIdStore)
				.setConnectionTimeToLive(60, TimeUnit.SECONDS);
		clientBuilder.setRedirectStrategy(new LaxRedirectStrategy());

		if (hi.isUseProxy()) {
			clientBuilder.setProxy(new HttpHost(hi.getProxyHost(), hi.getProxyPort()));
		}

		CloseableHttpClient client = clientBuilder.build();
		return client;
	}
}
