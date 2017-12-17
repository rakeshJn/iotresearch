package com.ibm.pn.nr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.ArcHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.NameHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.NodeHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PNTypeHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PlaceHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.TransitionHLAPI;

public class NRToPN2 {

	
	//private static String JSON = "[{\"id\":\"3605506d.13ea28\",\"type\":\"inject\",\"z\":\"537f6244.50253c\",\"name\":\"Temp\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":275,\"y\":141,\"wires\":[[\"f21d5751.1ca54\"]]},{\"id\":\"e48ad5.ea968528\",\"type\":\"inject\",\"z\":\"537f6244.50253c\",\"name\":\"Speed\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":280,\"y\":247,\"wires\":[[\"f21d5751.1ca54\"]]},{\"id\":\"ccd0ae63.5d947\",\"type\":\"inject\",\"z\":\"537f6244.50253c\",\"name\":\"Vibration\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":279,\"y\":347,\"wires\":[[\"f21d5751.1ca54\"]]},{\"id\":\"af9048e5.01c33\",\"type\":\"trigger\",\"z\":\"537f6244.50253c\",\"op1\":\"1\",\"op2\":\"0\",\"op1type\":\"str\",\"op2type\":\"str\",\"duration\":\"0\",\"extend\":false,\"units\":\"ms\",\"reset\":\"\",\"name\":\"Alarm\",\"x\":740.5,\"y\":159.75,\"wires\":[[]]},{\"id\":\"f21d5751.1ca54\",\"type\":\"function\",\"z\":\"537f6244.50253c\",\"name\":\"Compute Node\",\"func\":\"\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"location\":\"6e4d9d36.e0a3fc\",\"x\":526.5,\"y\":241.75,\"wires\":[[\"af9048e5.01c33\"]]},{\"id\":\"6e4d9d36.e0a3fc\",\"type\":\"location\",\"z\":\"\",\"name\":\"Room1\",\"host\":\"9.1.75.78\",\"port\":\"1880\"}]";
	private static String JSON = "[{\"id\":\"c77c179d.5d8e9\",\"type\":\"inject\",\"z\":\"75679841.8c14d\",\"name\":\"Temp\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":110,\"y\":148,\"wires\":[[\"c17173a1.3d1d7\"]]},{\"id\":\"3474170e.715a1\",\"type\":\"inject\",\"z\":\"75679841.8c14d\",\"name\":\"Speed\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":115,\"y\":254,\"wires\":[[\"c17173a1.3d1d7\"]]},{\"id\":\"3dcbdd2d.b7599a\",\"type\":\"inject\",\"z\":\"75679841.8c14d\",\"name\":\"Vibration\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":114,\"y\":354,\"wires\":[[\"c17173a1.3d1d7\"]]},{\"id\":\"c17173a1.3d1d7\",\"type\":\"function\",\"z\":\"75679841.8c14d\",\"name\":\"Compute Node\",\"func\":\"\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"location\":\"6e4d9d36.e0a3fc\",\"x\":361.5,\"y\":248.75,\"wires\":[[\"8d0a8ad6.a2d2\"]]},{\"id\":\"8d0a8ad6.a2d2\",\"type\":\"debug\",\"z\":\"75679841.8c14d\",\"name\":\"\",\"active\":true,\"console\":\"false\",\"complete\":\"payload\",\"location\":\"c0b529c8.67f2f8\",\"x\":578.5,\"y\":166.5,\"wires\":[]},{\"id\":\"6e4d9d36.e0a3fc\",\"type\":\"location\",\"z\":\"\",\"name\":\"Room1\",\"host\":\"9.1.75.78\",\"port\":\"1880\"},{\"id\":\"c0b529c8.67f2f8\",\"type\":\"location\",\"z\":\"\",\"name\":\"Room2\",\"host\":\"9.1.75.116\",\"port\":\"1880\"}]";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		//JSONObject j = new JSONObject(JSON);
		JSONArray ja = new JSONArray(JSON);
		
		Map<String, List<String>> transitions = new HashMap<String, List<String>>();
		List<String> allNodes = new ArrayList<String>();
		Map<String, String> node_loc = new HashMap<>();
		
		for(int i=0; i<ja.length(); ++i)
		{
			JSONObject jo = ja.getJSONObject(i);
			
			String id = (String)jo.get("id");
			allNodes.add(id);
			//System.out.println(id);
			
			List<String> tgtNodes = new ArrayList<String>();
			transitions.put(id, tgtNodes);
			
			if(jo.has("location"))
			{
				String loc = jo.getString("location");
				if( loc != null)
					node_loc.put(jo.getString("id"), loc);
			}
			if(!jo.has("wires"))
				continue;
			
			JSONArray jaw = jo.getJSONArray("wires");
			if(jaw!=null && jaw.length()>0)
			{
				JSONArray nodeIds = jaw.getJSONArray(0);  //there can be multiple arrays of wires
				
				for(int j=0; j<nodeIds.length(); ++j)
				{
					String tgtId = (String)nodeIds.get(j);
					transitions.get(id).add(tgtId);				
				}
			}
		}
		//System.out.println(transitions);
		
		//p/n
		
		try {
			ModelRepository.getInstance().createDocumentWorkspace("void");
			ModelRepository.getInstance().setPrettyPrintStatus(true);
			PetriNetDocHLAPI doc = new PetriNetDocHLAPI();
			PetriNetHLAPI net = new PetriNetHLAPI("net0", PNTypeHLAPI.COREMODEL,new NameHLAPI("hello"), doc);
			PageHLAPI page = new PageHLAPI("toppage", new NameHLAPI("myname"),null, net); //use of "null" is authorized but not encouraged 
						
			Map<String, TransitionHLAPI> trs = new HashMap<>();
			List<TransitionHLAPI> trans = new ArrayList<>();
			Map<TransitionHLAPI, List<TransitionHLAPI>> trToTrs = new HashMap<>();
			
			//all nodes are tr (except config nodes - todo)
			
			for(String tr : transitions.keySet())
			{
				//System.out.println(place);				
				if(node_loc.get(tr) != null)
				{
					TransitionHLAPI t = new TransitionHLAPI("T"+tr);
					t.setNameHLAPI(new NameHLAPI("L"+node_loc.get(tr)));
					
					t.setContainerPageHLAPI(page);
					trToTrs.put(t, new ArrayList<TransitionHLAPI>());
					trs.put(tr, t);
				}								
				
			}
		
			//create source node to target nodes mapping
			for(String tr : transitions.keySet())
			{	
				for(String tgt : transitions.get(tr))
				{
					TransitionHLAPI tgtPl = null;
					if (!trs.containsKey(tgt))
					{
						tgtPl = new TransitionHLAPI("T"+tgt);
						tgtPl.setContainerPageHLAPI(page);	
						if(node_loc.get(tgt) != null)
						{							
							tgtPl.setNameHLAPI(new NameHLAPI("L"+node_loc.get(tgt)));
						}						
					}
					else
						tgtPl = trs.get(tgt);
					
					trToTrs.get(trs.get(tr)).add(tgtPl);					
				}
			}
			//remove node with no connectivity (config node etc)
//			List<PlaceHLAPI> placesToRemove = new ArrayList<>();
//			
//			for (PlaceHLAPI p: placeToPlaces.keySet())
//			{
//				if (placeToPlaces.get(p).size()==0)
//					placesToRemove.add(p);
//			}
//			System.out.println("Places to remove "+placesToRemove.size());
//			for (PlaceHLAPI p: placesToRemove)
//			{
//				placeToPlaces.remove(p);
//			}
			
			int k=0;
			List<PlaceHLAPI> places = new ArrayList<>();
			for( TransitionHLAPI srcTr: trToTrs.keySet())
			{
				//System.out.println("Source tr "+srcTr.getId());
				for(TransitionHLAPI tgtTr : trToTrs.get(srcTr))
				{
					PlaceHLAPI tgtPl = null;
					//List<PlaceHLAPI> places = page.getObjects_PlaceHLAPI();
					for(PlaceHLAPI pl: places)
					{
						//System.out.println(pl.getId());
						if(pl.getId().equals("P"+(tgtTr.getId().substring(1))))
						{
							//System.out.println("Break");
							tgtPl=pl;
							break;
						}

					}
					if (tgtPl == null)
					{
						//System.out.println("Create new place ="+tgtTr.getId());
						tgtPl = new PlaceHLAPI("P"+tgtTr.getId().substring(1));
						tgtPl.setContainerPageHLAPI(page);
						places.add(tgtPl);
					}
					//trans.add(ts);
					//ts.getContainedItem().getToolspecifics().get(0).getContainerLabel().
					if(!doesArcExist(srcTr, tgtPl, page.getObjects_ArcHLAPI()))
						new ArcHLAPI("a"+k, srcTr, tgtPl, page);   // source place to transition arc
							
					if(!doesArcExist(tgtPl, tgtTr, page.getObjects_ArcHLAPI()))
						new ArcHLAPI("b"+k, tgtPl, tgtTr, page); //transition to target place arc							
					k++;
				}
			}
			
			System.out.println(doc.toPNML());
			
		} catch (InvalidIDException | VoidRepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loggerContext.stop();
		System.out.println("\n");
		System.out.println(JSON);
	}
	private static boolean doesArcExist(NodeHLAPI src, NodeHLAPI tgt, List<ArcHLAPI> 	arcs)
	{		
		for(ArcHLAPI a: arcs)
		{
			if(a.getSourceHLAPI().getId().contentEquals(src.getId()) && a.getTargetHLAPI().getId().contentEquals(tgt.getId()))
				return true;
		}
		return false;
	}
}
