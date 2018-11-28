package com.ibm.ds.nr;

import java.util.List;

import org.json.JSONObject;

public class AbstractLocation extends Location{

	private List<PlatformLocation> platformLocations;
	
	public AbstractLocation(JSONObject jo)
	{
		super(jo);
		//set PL etc
	}
	
	public List<PlatformLocation> getPlatformLocations() {
		return platformLocations;
	}

	public void setPlatformLocations(List<PlatformLocation> platformLocations) {
		this.platformLocations = platformLocations;
	}
	
}
