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
import fr.lip6.move.pnml.pnmlcoremodel.PNType;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.ArcHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.NameHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PNTypeHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PlaceHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.TransitionHLAPI;

public class NRToPN {

	
	private static String JSON = "[{\"id\":\"3605506d.13ea28\",\"type\":\"inject\",\"z\":\"537f6244.50253c\",\"name\":\"Temp\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":275,\"y\":141,\"wires\":[[\"f21d5751.1ca54\"]]},{\"id\":\"e48ad5.ea968528\",\"type\":\"inject\",\"z\":\"537f6244.50253c\",\"name\":\"Speed\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":280,\"y\":247,\"wires\":[[\"f21d5751.1ca54\"]]},{\"id\":\"ccd0ae63.5d947\",\"type\":\"inject\",\"z\":\"537f6244.50253c\",\"name\":\"Vibration\",\"topic\":\"\",\"location\":\"6e4d9d36.e0a3fc\",\"payload\":\"\",\"payloadType\":\"date\",\"repeat\":\"\",\"crontab\":\"\",\"once\":false,\"x\":279,\"y\":347,\"wires\":[[\"f21d5751.1ca54\"]]},{\"id\":\"af9048e5.01c33\",\"type\":\"trigger\",\"z\":\"537f6244.50253c\",\"op1\":\"1\",\"op2\":\"0\",\"op1type\":\"str\",\"op2type\":\"str\",\"duration\":\"0\",\"extend\":false,\"units\":\"ms\",\"reset\":\"\",\"name\":\"Alarm\",\"x\":740.5,\"y\":159.75,\"wires\":[[]]},{\"id\":\"f21d5751.1ca54\",\"type\":\"function\",\"z\":\"537f6244.50253c\",\"name\":\"Compute Node\",\"func\":\"\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"location\":\"6e4d9d36.e0a3fc\",\"x\":526.5,\"y\":241.75,\"wires\":[[\"af9048e5.01c33\"]]},{\"id\":\"6e4d9d36.e0a3fc\",\"type\":\"location\",\"z\":\"\",\"name\":\"Room1\",\"host\":\"9.1.75.78\",\"port\":\"1880\"}]";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		//JSONObject j = new JSONObject(JSON);
		JSONArray ja = new JSONArray(JSON);
		
		Map<String, List<String>> transitions = new HashMap<String, List<String>>();
		List<String> allNodes = new ArrayList<String>();
		
		for(int i=0; i<ja.length(); ++i)
		{
			JSONObject jo = ja.getJSONObject(i);
			
			String id = (String)jo.get("id");
			allNodes.add(id);
			//System.out.println(id);
			
			List<String> tgtNodes = new ArrayList<String>();
			transitions.put(id, tgtNodes);
			
			if(!jo.has("wires"))
				continue;
			
			JSONArray jaw = jo.getJSONArray("wires");
			JSONArray nodeIds = jaw.getJSONArray(0);  //there can be multiple arrays of wires
			
			for(int j=0; j<nodeIds.length(); ++j)
			{
				String tgtId = (String)nodeIds.get(j);
				transitions.get(id).add(tgtId);				
			}
		}
		//System.out.println(transitions);
		
		//p/n
		
		try {
			ModelRepository.getInstance().createDocumentWorkspace("void");
			ModelRepository.getInstance().setPrettyPrintStatus(true);
			PetriNetDocHLAPI doc = new PetriNetDocHLAPI();
			PetriNetHLAPI net = new PetriNetHLAPI("net0", PNTypeHLAPI.COREMODEL,new NameHLAPI("hello"), doc);
			net.setTypeHLAPI(PNType.COREMODEL);
			PageHLAPI page = new PageHLAPI("toppage", new NameHLAPI("myname"),null, net); //use of "null" is authorized but not encouraged 
						
			Map<String, PlaceHLAPI> places = new HashMap<>();
			List<TransitionHLAPI> trans = new ArrayList<>();
			Map<PlaceHLAPI, List<PlaceHLAPI>> placeToPlaces = new HashMap<>();
			
			//all nodes are places (except config nodes - todo)
			
			for(String place : transitions.keySet())
			{
				//System.out.println(place);
				PlaceHLAPI pl = new PlaceHLAPI("P"+place);
				pl.setContainerPageHLAPI(page);
				
				placeToPlaces.put(pl, new ArrayList<PlaceHLAPI>());
				places.put(place, pl);
			}
		
			//create source node to target nodes mapping
			for(String place : transitions.keySet())
			{	
				for(String tgt : transitions.get(place))
				{
					PlaceHLAPI tgtPl = null;
					if (!places.containsKey(tgt))
					{
						tgtPl = new PlaceHLAPI("P"+tgt);
						tgtPl.setContainerPageHLAPI(page);	
					}
					else
						tgtPl = places.get(tgt);
					
					placeToPlaces.get(places.get(place)).add(tgtPl);					
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
			for( PlaceHLAPI p: placeToPlaces.keySet())
			{
				//System.out.println(placeToPlaces.get(p).size());
				for(PlaceHLAPI tgtPlace : placeToPlaces.get(p))
				{
					TransitionHLAPI ts = new TransitionHLAPI("TR-"+p.getId()+"_"+tgtPlace.getId());
					ts.setContainerPageHLAPI(page);
					trans.add(ts);
					//ts.getContainedItem().getToolspecifics().get(0).getContainerLabel().
					new ArcHLAPI("a"+k, p, ts, page);   // source place to transition arc
					//System.out.println(p.getId()+" -->TR-"+tgtPlace);		
					
					new ArcHLAPI("b"+k, ts, tgtPlace, page); //transition to target place arc
					//System.out.println(p.getId()+" -->TR-"+tgtPlace);		
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

}
