package com.ibm.de.duedorf.wscs.qwcapture;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;



public class ConfigTest {

	@Test
	public void testConfig() {
		Configuration c =null;
		try {
			 c =Configuration.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
	

		HostInfo h = c.getHostInfo().get(0);
		Assert.assertEquals(12345, h.getPort());
		Assert.assertEquals("node1", h.getNodeName());
		Assert.assertEquals(false, h.isUsesSSL());
		Assert.assertEquals("ca1", h.getCertName());
		Assert.assertEquals(false, h.isUseProxy());
		Assert.assertEquals("9.9.9.9", h.getProxyHost());
		Assert.assertEquals(8080, h.getProxyPort());
		Assert.assertEquals("admin", h.getUser());
		Assert.assertEquals("password", h.getPass());
		
		h = c.getHostInfo().get(1);
		Assert.assertEquals(432, h.getPort());
	}
}
