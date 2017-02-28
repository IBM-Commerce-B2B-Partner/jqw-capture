
package com.ibm.de.duedorf.wscs.qwcapture;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * A example that demonstrates how HttpClient APIs can be used to perform
 * form-based logon.
 */
public class Capture {

	Logger logger = Logger.getLogger(Capture.class.getName());

	public static void main(String[] args) throws Exception {
		Capture captureTool = new Capture();
		captureTool.go();
	}

	public void go() throws Exception {
		
		Configuration config = Configuration.getInstance();
		for (HostInfo host : config.getHostInfo()) {
			if (host.isInuse()){
			logger.log(Level.INFO, "Checking on host {0}\n", host.getNodeName());
			processHost(host);
			} else {
				logger.log(Level.INFO, " host {0} is not in use\n", host.getNodeName());
			}
		}

	}

	private void processHost(HostInfo host) throws Exception {
		CloseableHttpClient client = Client.createClient(host);

		String token = CsrfProvider.getCsrfToken(client, host);
		logger.log(Level.INFO, "got token {0}", token);

	}
}
