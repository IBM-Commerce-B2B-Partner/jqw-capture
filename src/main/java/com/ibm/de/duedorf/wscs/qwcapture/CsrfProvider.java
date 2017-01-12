package com.ibm.de.duedorf.wscs.qwcapture;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

public class CsrfProvider {
	
	public final Configuration config;
	
	
	
	public CsrfProvider(Configuration config) {
		super();
		this.config = config;
	}



	public String getCsrfToken(CloseableHttpClient client) throws Exception {
		String.format("http://%s:%s/queueWatch", config.getRemoteHost(), config.getRemotePort());
		
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
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse("");
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile("//blub");
		String rc = (String)expr.evaluate(doc, XPathConstants.STRING);
		return rc;
	}

}
