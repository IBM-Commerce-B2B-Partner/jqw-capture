package com.ibm.de.duedorf.wscs.qwcapture;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;



public class ConfigTest {

	@Test
	public void testConfig() {
		Capture ca = new Capture();
		
		Configuration c =null;
		try {
			 c = ca.readConfig(Capture.CONFIG_FILE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(false, c.isUseProxy());
		Assert.assertEquals("9.9.9.9", c.getRemoteHost());
		Assert.assertEquals(18800, c.getRemotePort());
		Assert.assertEquals("9.9.9.9", c.getProxyHost());
		Assert.assertEquals(8080, c.getProxyPort());
	}
}
