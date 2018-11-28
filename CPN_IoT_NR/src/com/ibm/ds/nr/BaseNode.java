package com.ibm.ds.nr;

import org.json.JSONObject;

public class BaseNode {

	protected String id;
	protected String type;
	protected String name;
	protected JSONObject json;
	
	public BaseNode(JSONObject jo)
	{
		this.id = jo.getString("id");
		this.name = jo.getString("name");
		this.type = jo.getString("type");
		this.json = jo;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}
