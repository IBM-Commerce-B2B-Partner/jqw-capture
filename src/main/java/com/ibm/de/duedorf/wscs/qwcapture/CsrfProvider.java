package com.ibm.de.duedorf.wscs.qwcapture;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class CsrfProvider {
	private static final Logger logger = Logger.getLogger(CsrfProvider.class.getName());

	public static byte[] buildBody(HostInfo hi) throws UnsupportedEncodingException {
		if ((hi.getUser() == null) || (hi.getUser().length() == 0)) {
			logger.log(Level.SEVERE, "No User specified in Host Info {0}", hi);
			throw new InvalidParameterException(String.format("No User specified in Host Info {0}", hi));
		}
		if ((hi.getPass() == null) || (hi.getPass().length() == 0)) {
			logger.log(Level.SEVERE, "No Password specified in Host Info {0}", hi);
			throw new InvalidParameterException(String.format("No Password specified in Host Info {0}", hi));
		}
		StringBuffer sb = new StringBuffer();
		sb.append("username=");
		sb.append(hi.getUser());
		sb.append("&");
		sb.append("password=");
		sb.append(hi.getPass());
		logger.log(Level.INFO, "Created login string {0}\n", sb.toString());
		return sb.toString().getBytes("UTF-8");
	}

	public static String getCsrfToken(CloseableHttpClient client, HostInfo hi) throws Exception {
		HttpHost host = new HttpHost(hi.getHostname(), hi.getPort());
		String destination = String.format("/queueWatch/checkautho.jsp", hi.getHostname(), hi.getPort());

		HttpPost httpPost = new HttpPost(destination);

		httpPost.setHeader("Host",hi.getHostname()+":"+hi.getPort());
//		httpPost.setHeader("User-Agent","curl/7.52.1");
		httpPost.setHeader("Accept", "*/*");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setEntity(new ByteArrayEntity(buildBody(hi)));

		CloseableHttpResponse response = client.execute(host, httpPost);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = null;

		try {
			HttpEntity responseEntity = response.getEntity();
			doc = builder.parse(responseEntity.getContent());
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			response.close();
		}
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile("//body/form/@action");
		Node node = (Node) expr.evaluate(doc.getDocumentElement(), XPathConstants.NODE);
		
//		logger.log(Level.INFO, "node {0}", node.toString());
		StringTokenizer st = new StringTokenizer(node.getNodeValue(), "=");
		st.nextToken();
		
		return st.nextToken();
	}

}
