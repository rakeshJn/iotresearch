package com.ibm.ds.nr;

import org.json.JSONObject;

public class Location extends BaseNode {


enum LOC_TYPE{
	PLATFORM,
	ABSTRACT;
};

	public Location(JSONObject jo)
	{
		super(jo);
	}
}
