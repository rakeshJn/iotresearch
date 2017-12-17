package com.ibm.pn.nr;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.PNMLUtils;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.ArcHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.NodeHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.TransitionHLAPI;

public class PNToNR3 {

	
	public static void main(String[] args) 
	{
		String path = "/Users/rakesh/eclipse-workspace2/PN_NR_Proj/Ref/data5_L2.pnml";
		
		String json = "/Users/rakesh/eclipse-workspace2/PN_NR_Proj/Ref/data5.json";
		
		 
        File f = new File(path);
        
        JSONArray ret = new JSONArray();
        try {
        			FileReader fr = new FileReader(json);
        			JSONTokener tokener = new JSONTokener(fr);
        			JSONArray ja = new JSONArray(tokener); 
        			
        			System.out.println(((JSONObject)ja.get(0)).get("id"));
        			
        			
                // Load the document. No fall back to any compatible type (false).
                // Fall back takes place between an unknown Petri Net type and the CoreModel.
                HLAPIRootClass rc = PNMLUtils.importPnmlDocument(f, false);
                // Determine the Petri Net Document type... 
                
                if (PNMLUtils.isCoreModelDocument(rc)) 
	            {
	                	fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI coreDoc =
	                            (fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI)rc;
	                	
	                	List<PetriNetHLAPI> nets = coreDoc.getNetsHLAPI();
	                	if(nets.size() == 0) throw new Exception ("No nets found");
	                	
	                	PetriNetHLAPI net = nets.get(0);
	                	
	                	PageHLAPI page = net.getPagesHLAPI().get(0);
	                System.out.println("isCoreModelDocument");
	                    
	                List<ArcHLAPI> 			arcs 		= page.getObjects_ArcHLAPI();
	                System.out.println("Total arcs "+arcs.size());
	                Set<String> processed = new HashSet<>();
	                
	                for(ArcHLAPI arc: arcs)
	                {
	                		NodeHLAPI src = arc.getSourceHLAPI();
	                		NodeHLAPI tgt = arc.getTargetHLAPI();
	                		TransitionHLAPI tr = null;
	                		Set<TransitionHLAPI> wires = new HashSet<>();	                		
	                		boolean trIsSrc = false;
	                		
	                		if(src instanceof TransitionHLAPI)
	                		{
	                			tr = (TransitionHLAPI)src;
	                			trIsSrc = true;
	                		}
	                		else if(tgt instanceof TransitionHLAPI)
	                			tr = (TransitionHLAPI)tgt;
	                		
	                		if(processed.contains(tr.getId()))
	                			continue;
	                		processed.add(tr.getId());
	                		
	                		wires = getTargetTransitions(arc, arcs);   //this looks up for wires even if the transition is in target place for this arc	                			               			
	                		
	                		System.out.println("Transition "+tr.getId());
	                		JSONObject node = getNRNode(tr, wires, ja);
	                		if(node != null)
	                			ret.put(node);
	                }
	                
	            }
                System.out.println("NR Json ");
                System.out.println(ret.toString(4));
                
                fr.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
	}
	
	private static JSONObject getNRNode(TransitionHLAPI tr, Set<TransitionHLAPI> wires, JSONArray ja)
	{
		JSONObject ret = null;
		String trid = getNodeId(tr);
		if(trid.startsWith("mqtt"))
			return handleMQTT(tr, wires);
		
		Iterator iter = ja.iterator();		
		while(iter.hasNext())
		{
			JSONObject jo = (JSONObject)iter.next();
			System.out.println(jo.getString("id")+"====="+trid);
			if(jo.getString("id").contentEquals(trid))
			{
				//ret.put("id", trnm);
				//ret.put("type", jo.get("type"));
				System.out.println(jo);
				ret = jo;
				if(ret.has("location"))
					ret.remove("location");
				if(ret.has("wires"))
					ret.remove("wires");	
				if(ret.has("z"))
					ret.remove("z");	
			}
		}
		JSONArray w1 = new JSONArray();
		for(TransitionHLAPI t: wires)
		{
			String nodeid = getNodeId(t);  
			w1.put(nodeid);
		}
		JSONArray w0 = new JSONArray();
		w0.put(w1);
		if(ret != null)
			ret.put("wires", w0);
		else
		{
			ret = new JSONObject();
			ret.put("id", trid);
		}
		return ret;
	}
	
	private static JSONObject handleMQTT(TransitionHLAPI tr, Set<TransitionHLAPI> wires)
	{
		JSONObject jo = new JSONObject();
		String trid = getNodeId(tr);
		jo.put("id", trid);
		jo.put("broker", "");
		jo.put("qos", "");
		jo.put("x", 100);
		jo.put("y", 100);
		if(trid.startsWith("mqtt.in"))
		{	
			jo.put("type", "mqtt in");
			jo.put("topic", trid.substring(7));
		}
		else
		{
			jo.put("type", "mqtt out");
			jo.put("topic", trid.substring(8));
		}
		JSONArray w1 = new JSONArray();
		for(TransitionHLAPI t: wires)
		{
			String nodeid = getNodeId(t);  
			w1.put(nodeid);
		}
		JSONArray w0 = new JSONArray();
		w0.put(w1);		
		jo.put("wires", w0);
		return jo;
	}
	
	//[{"id":"fde024d2.22ad8","type":"mqtt in","z":"21362410.4291cc","name":"","topic":"","qos":"2","x":457.5,"y":419.25,"wires":[[]]}]
	
	private static Set<TransitionHLAPI> getTargetTransitions(ArcHLAPI srcArc, List<ArcHLAPI> allArcs)
	{
		Set<TransitionHLAPI> ret = new HashSet<>();
		if(!(srcArc.getSourceHLAPI() instanceof TransitionHLAPI))
		{
			//tr is in tgt place
			TransitionHLAPI t = (TransitionHLAPI)srcArc.getTargetHLAPI();
			for(ArcHLAPI a: allArcs)
			{
				if(a.getSource().getId().contentEquals(t.getId())) //found an arc where this tr is a source
				{
					return getTargetTransitions(a, allArcs);
				}
			}
			//return ret;
		}
		NodeHLAPI tgtpl = srcArc.getTargetHLAPI();
		String tgtplid = tgtpl.getId();
		for(ArcHLAPI a: allArcs)
		{
			if(a.getSourceHLAPI().getId().contentEquals(tgtplid))
			{
				System.out.println("%%%%%% "+a.getSourceHLAPI().getId());
				ret.add((TransitionHLAPI)a.getTargetHLAPI());  //there is an arc where given place is a source, the target of that arc is connected transition
				break;
			}
		}
		return ret;
	}
	
	private static String getNodeId(TransitionHLAPI tr)
	{		
		return tr.getId().substring(1); //remove T		
	}
	
}
