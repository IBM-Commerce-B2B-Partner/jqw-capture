package com.ibm.de.duedorf.wscs.qwcapture;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Configuration {
	public static final String CONFIG_FILE = "config.yml";
	
	
	private List<HostInfo> hostInfo;
	
	public List<HostInfo> getHostInfo() {
		return hostInfo;
	}

	public void setHostInfo(List<HostInfo> hostInfo) {
		this.hostInfo = hostInfo;
	}

	private static Configuration configuration = null;
	
	public synchronized static Configuration getInstance() throws IOException {
		
		if ( Configuration.configuration == null){
			Configuration.configuration = readConfig(CONFIG_FILE);
		}
		return Configuration.configuration;
	}
	
	/**
	 * Reads the YAML config from the filesystem
	 * @param configFile
	 * @return
	 * @throws IOException
	 */
	private static Configuration readConfig(String configFile) throws IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Configuration config = null;
		config = mapper.readValue( Thread.currentThread().getContextClassLoader().getResource(CONFIG_FILE), Configuration.class);
		return config;
	}
	
	/**
	 * Private constructor for singleton
	 */
	private Configuration() {
		super();
	}

	
	
}
