
package com.ibm.de.duedorf.wscs.qwcapture;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * A example that demonstrates how HttpClient APIs can be used to perform
 * form-based logon.
 */
public class Capture {
	public static final String CONFIG_FILE = "config.yml";
	public Configuration config = null;

	public Configuration readConfig(String configFile) throws IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Configuration config = null;
		config = mapper.readValue(new File(configFile), Configuration.class);
		return config;
	}

	public CloseableHttpClient createClient() {
		BasicCookieStore sessionIdStore = new BasicCookieStore();
		HttpClientBuilder rc = HttpClients.custom().setDefaultCookieStore(sessionIdStore).setConnectionTimeToLive(60,
				TimeUnit.SECONDS);
		if (config.isUseProxy()) {
			rc.setProxy(new HttpHost(config.getProxyHost(), config.getProxyPort()));
		}

		CloseableHttpClient client = rc.build();
		return client;
	}

	public static void main(String[] args) throws Exception {
		Capture captureTool = new Capture();
		captureTool.go();
	}

	public void go() throws Exception {
		CloseableHttpClient client = this.createClient();
		config = readConfig(CONFIG_FILE);
		CsrfProvider provider = new CsrfProvider(config);
		String token = provider.getCsrfToken(client);

		try {
			HttpGet httpget = new HttpGet("https://someportal/");
			CloseableHttpResponse response1 = client.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();

				System.out.println("Login form get: " + response1.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Initial set of cookies:");
			} finally {
				response1.close();
			}

			HttpUriRequest login = RequestBuilder.post().setUri(new URI("https://someportal/"))
					.addParameter("IDToken1", "username").addParameter("IDToken2", "password").build();
			CloseableHttpResponse response2 = client.execute(login);
			try {
				HttpEntity entity = response2.getEntity();

				System.out.println("Login form get: " + response2.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Post logon cookies:");

			} finally {
				response2.close();
			}
		} finally {
			client.close();
		}
	}
}
