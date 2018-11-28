package com.ibm.ds.nr;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.iot.cpntools.base.Trans;
import com.ibm.iot.cpntools.base.Place;


public class NRNode extends BaseNode{
	
	private Location location;
	private int intId;
	private String[] targetNodeIds = new String[0];
	private boolean isTargetNodeSame = true;
	
	private Map<Location, Place> incomingLocPlaceMap = new HashMap<>();	
	private Trans tansition;
	private Place place;
	//private  LOC_TYPE loc_type;
	
	public NRNode(JSONObject jo, Location location, int intId)
	{
		super(jo);
		this.location = location;
		this.intId = intId;
		
		if(jo.has("wires"))
		{
			JSONArray jaw = jo.getJSONArray("wires");
			if(jaw!=null && jaw.length()>0)
			{
				JSONArray nodeIds = jaw.getJSONArray(0);  //there can be multiple arrays of wires
				
				targetNodeIds = new String[nodeIds.length()];
				
				for(int j=0; j<nodeIds.length(); ++j)
				{
					String tgtId = (String)nodeIds.get(j);
					targetNodeIds[j] = tgtId;				
				}
			}
		}
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getIntId() {
		return intId;
	}

	public void setIntId(int intId) {
		this.intId = intId;
	}

	public String[] getTargetNodeIds() {
		return targetNodeIds;
	}

	public void setTargetNodeIds(String[] targetNodeIds) {
		this.targetNodeIds = targetNodeIds;
	}

	public boolean isTargetNodeSame() {
		return isTargetNodeSame;
	}

	public void setTargetNodeSame(boolean isTargetNodeSame) {
		this.isTargetNodeSame = isTargetNodeSame;
	}

	public Map<Location, Place> getIncomingLocPlaceMap() {
		return incomingLocPlaceMap;
	}

	public void setIncomingLocPlaceMap(Map<Location, Place> incomingLocPlaceMap) {
		this.incomingLocPlaceMap = incomingLocPlaceMap;
	}

	public Trans getTansition() {
		return tansition;
	}

	public void setTansition(Trans tansition) {
		this.tansition = tansition;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}
}
