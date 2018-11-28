package com.ibm.ds.nr;

import org.json.JSONObject;

public class PlatformLocation extends Location {
	
	private String host;
	private String port;
	//private credentials;
	
	public PlatformLocation(JSONObject jo)
	{
		super(jo);
		//set host, port etc
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
