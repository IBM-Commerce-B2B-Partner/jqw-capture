package com.ibm.de.duedorf.wscs.qwcapture;

public class HostInfo {
	private String nodeName;
	private String hostname;
	private int port;
	private boolean usesSSL;
	private String certName;
	private boolean useProxy;
	private String proxyHost;
	private int proxyPort;
	private String user;
	private String pass;
	private boolean inuse;
	
	
	
	public boolean isInuse() {
		return inuse;
	}
	public void setInuse(boolean inuse) {
		this.inuse = inuse;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public boolean isUseProxy() {
		return useProxy;
	}
	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}
	public String getProxyHost() {
		return proxyHost;
	}
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	public int getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getCertName() {
		return certName;
	}
	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isUsesSSL() {
		return usesSSL;
	}
	public void setUsesSSL(boolean usesSSL) {
		this.usesSSL = usesSSL;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Host Info\n");
		sb.append("nodeName ").append(nodeName);
		sb.append("hostname ").append(hostname);
		sb.append("port ").append(port);
		sb.append("usesSSL ").append(usesSSL);
		sb.append("certName ").append(certName);
		sb.append("useProxy ").append(useProxy);
		sb.append("proxyHost ").append(proxyHost);
		sb.append("proxyPort ").append(proxyPort);
		sb.append("user ").append(user);
		sb.append("pass ").append(pass);

		return sb.toString();
	}
	
	
}
