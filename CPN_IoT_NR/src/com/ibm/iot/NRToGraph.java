package com.ibm.iot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.ds.Digraph;
import com.ibm.ds.nr.AbstractLocation;
import com.ibm.ds.nr.Location;
import com.ibm.ds.nr.NRNode;
import com.ibm.ds.nr.PlatformLocation;
import com.ibm.iot.cpntools.base.Arc;
import com.ibm.iot.cpntools.base.Place;
import com.ibm.iot.cpntools.base.Placeend;
import com.ibm.iot.cpntools.base.Trans;
import com.ibm.iot.cpntools.base.Transend;

public class NRToGraph {

	private static String JSON = "[{\"id\":\"2f9561de.333fee\",\"type\":\"inject\",\"z\":\"e67cf5a0.5c0e88\",\"name\":\"Occupancy Sensor\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":252.2222137451172,\"y\":153.3333282470703,\"wires\":[[\"94a77544.1f4678\"]]},{\"id\":\"94a77544.1f4678\",\"type\":\"function\",\"z\":\"e67cf5a0.5c0e88\",\"name\":\"Gateway\",\"func\":\"\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"location\":\"7c63fb4a.8d87b4\",\"x\":510.21366119384766,\"y\":225.09828186035156,\"wires\":[[\"7835fa48.257004\"]]},{\"id\":\"7835fa48.257004\",\"type\":\"debug\",\"z\":\"e67cf5a0.5c0e88\",\"name\":\"Cloud\",\"active\":true,\"console\":\"false\",\"complete\":\"payload\",\"location\":\"30d98a41.91ebc6\",\"x\":669.2136611938477,\"y\":224.84828186035156,\"wires\":[]},{\"id\":\"b2114c1a.76cdc\",\"type\":\"inject\",\"z\":\"e67cf5a0.5c0e88\",\"name\":\"Occupancy Sensor\",\"topic\":\"\",\"location\":\"c0b529c8.67f2f8\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":248.88888549804688,\"y\":227.77777099609375,\"wires\":[[\"94a77544.1f4678\"]]},{\"id\":\"b4c06481.8440b8\",\"type\":\"inject\",\"z\":\"e67cf5a0.5c0e88\",\"name\":\"Occupancy Sensor\",\"topic\":\"\",\"location\":\"be1b3196.498498\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":246.66665649414062,\"y\":298.8888854980469,\"wires\":[[\"94a77544.1f4678\"]]},{\"id\":\"6e4d9d36.e0a3fc\",\"type\":\"location\",\"z\":\"\",\"name\":\"Room1\",\"host\":\"9.1.75.78\",\"port\":\"1880\"},{\"id\":\"7c63fb4a.8d87b4\",\"type\":\"location\",\"z\":\"\",\"name\":\"Gateway\",\"host\":\"9.1.141.225\",\"port\":\"1880\",\"loc_type\":\"Platform\"},{\"id\":\"30d98a41.91ebc6\",\"type\":\"location\",\"z\":\"\",\"name\":\"Cloud\",\"host\":\"localhost\",\"port\":\"1880\",\"loc_type\":\"Platform\"},{\"id\":\"c0b529c8.67f2f8\",\"type\":\"location\",\"z\":\"\",\"name\":\"Room2\",\"host\":\"9.1.141.224\",\"port\":\"1880\",\"loc_type\":\"Platform\"},{\"id\":\"be1b3196.498498\",\"type\":\"location\",\"z\":\"\",\"name\":\"Room3\",\"host\":\"9.1.142.82\",\"port\":\"1880\",\"loc_type\":\"Platform\"}]";
	
	//private HashMap<>
	Map<String, Location> locations = new HashMap<>();
	Map<Integer, NRNode> intNodes = new HashMap<>();
	Map<String, NRNode> strNodes = new HashMap<>();
	Digraph dg = null;
	
	private void extractpLocations()	
	{
		JSONArray ja = new JSONArray(JSON);
	
		for(int i=0; i<ja.length(); ++i)
		{
			JSONObject jo = ja.getJSONObject(i);
			if(jo.has("type") && jo.getString("type").contentEquals("location"))
			{
				if(!jo.has("loc_type") || jo.getString("loc_type").contentEquals("Platform"))
				{
					 //its platform
					locations.put(jo.getString("name"), new PlatformLocation(jo));
				}
				else  //its abstract					
				{
					locations.put(jo.getString("name"), new AbstractLocation(jo));
				}
			}
		}
	}
	
	private void extractNodes()
	{
		JSONArray ja = new JSONArray(JSON);
		Integer nodeId = 0;
		for(int i=0; i<ja.length(); ++i)
		{
			JSONObject jo = ja.getJSONObject(i);
			if(jo.has("type") && jo.getString("type").contentEquals("location")) continue;  //TODO ignore some other types also
			else
			{				
				if(jo.has("location"))
				{
					
					String loc = jo.getString("location");
					NRNode node = new NRNode(jo, locations.get(loc), nodeId);
					//nodes.put(node.getId()+":"+nodeId, node);
					intNodes.put(nodeId, node);
					strNodes.put(node.getId(), node);
					nodeId++;
				}
				else
				{
					//TODO handle case when location is not present (shouldn't happen)
				}
			}
		}
	}
	
	private void createGraph()
	{
		dg = new Digraph(intNodes.size()); 
		for(int i: intNodes.keySet())
		{
			NRNode n = intNodes.get(i);
			String[] tgts = n.getTargetNodeIds();
			for(int j=0; j<tgts.length; j++)
			{
				String tgtNodeId = tgts[j];
				NRNode tgtNode = strNodes.get(tgtNodeId);
				
				int tgtNodeIntId = tgtNode.getIntId();
				Location tgtNodeLocn = tgtNode.getLocation();
				
				if(n.getLocation() != tgtNodeLocn)
					n.setTargetNodeSame(false);
				
				dg.addEdge(n.getIntId(), tgtNodeIntId);
			}		
		}
	}
	
	private void processCPNPreCond()
	{
		dg = new Digraph(intNodes.size()); 
		for(int i: intNodes.keySet())
		{
			NRNode n = intNodes.get(i);
			Map<Location, Place> locPlace = new HashMap<>();
			
			Trans t = new Trans();
			t.setId(n.getIntId()+"_"+n.getName());
			Transend te = new Transend();
			te.setIdref(t.getId());  //TODO
			
			n.setTansition(t);
			
			Place p = new Place();
			p.setId(n.getIntId()+"_"+n.getLocation().getName());
			Placeend pe = new Placeend();
			pe.setIdref(p.getId());
			
			n.setPlace(p);
			
			Arc arc = new Arc();
			arc.setId(p.getId()+"_"+t.getId());
			arc.setPlaceend(pe);
			arc.setTransend(te);
			arc.setOrientation("PToT");
			//arc.setOrder("1"); ?? seems to be required value
			
			Set<Integer> incN = dg.getIncoming(n.getIntId());
			for(int nid: incN)
			{
				Location l = intNodes.get(nid).getLocation();				
				locPlace.put(l,  p);				
			}
			n.setIncomingLocPlaceMap(locPlace);
		}
	}
	
	private void processCPNPostCond()
	{
		dg = new Digraph(intNodes.size()); 
		for(int i: intNodes.keySet())
		{
			NRNode n = intNodes.get(i);
			Transend te = new Transend();
			te.setIdref(n.getTansition().getId());
			
			String[] tgtNodes = n.getTargetNodeIds();
			for(String tgtNodeId: tgtNodes)
			{
				NRNode tgtNd = strNodes.get(tgtNodeId);
				Place tgtPl = tgtNd.getPlace();//getIncomingLocPlaceMap().get(n.getLocation());
				if(tgtPl != null) //should not be null 
				{
					Placeend pe = new Placeend();
					pe.setIdref(tgtPl.getId());
					
					if(n.getLocation() == tgtNd.getLocation())
					{
						//connect directly to tgtPl
						Arc arc = new Arc();
						arc.setId(tgtPl.getId()+"_"+te.getIdref());
						arc.setPlaceend(pe);
						arc.setTransend(te);
						arc.setOrientation("TToP");
					}
					else
					{
						//generate additional P-T
					}
				}				
			}
		}
	}
	
	public static void main(String args[])
	{
		NRToGraph nrg = new NRToGraph();
		nrg.extractpLocations();
		nrg.extractNodes();
		nrg.createGraph();
		System.out.println(nrg.dg);
		System.out.println(nrg.dg.getIncoming(1));
		
	}
}
