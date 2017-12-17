package com.ibm.pn.nr;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.PNMLUtils;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.ArcHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.NodeHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PlaceHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.TransitionHLAPI;

public class PNToNR2 {

	public static void main(String[] args) 
	{
		String path = "/Users/rakesh/eclipse-workspace2/PN_NR_Proj/Ref/data4.pnml";
		
		 // We assume the path to the PNML file is provided as argument to this program.
        File f = new File(path);
        JSONArray ret = new JSONArray();
        try {
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
	                    
	                List<PlaceHLAPI> places = page.getObjects_PlaceHLAPI();
	                List<TransitionHLAPI> transitions = page.getObjects_TransitionHLAPI();
	                List<ArcHLAPI> arcs = page.getObjects_ArcHLAPI();
	                
	                Map<PlaceHLAPI, List<PlaceHLAPI>> placeToPlaces = new HashMap<>();
	                
	                Map<String, String> pToT = new HashMap<>();  //place to transition map
	                Map<String, String> tToP = new HashMap<>();  //transition to place map
	                
	                Map<String, List<TransitionHLAPI>> 	loc_trans = new HashMap<>();
	                Map<String, List<PlaceHLAPI>> 		loc_places = new HashMap<>();
	                Map<String, List<ArcHLAPI>> 			loc_arcs = new HashMap<>();
	                
	                for(TransitionHLAPI tr: transitions)
	                {
		                	if(tr.getName() != null)
		                	{
		                		System.out.println(tr.getName().getText());
		                		if(loc_trans.containsKey(tr.getName().getText()))
		                		{	
		                			loc_trans.get(tr.getName().getText()).add(tr);
		                		}
		                		else
		                		{
		                			List<TransitionHLAPI> t = new ArrayList<>();
		                			t.add(tr);
		                			loc_trans.put(tr.getName().getText(), t);		        
		                		}
		                		List<ArcHLAPI> inarcs = tr.getInArcsHLAPI();
		                		for(ArcHLAPI arc: inarcs)
		                		{
		                			PlaceHLAPI pl = (PlaceHLAPI)arc.getSourceHLAPI();
		                			List<ArcHLAPI> plSrc = pl.getInArcsHLAPI();
		                			int k = 0;
		                			for(ArcHLAPI s: plSrc)
		                			{
		                				TransitionHLAPI t = (TransitionHLAPI)s.getSourceHLAPI();
		                				
		                				if(t.getName() != null && t.getName().getText().equals(tr.getName().getText()))
		                				{
		                					k++;
		                					loc_trans.get(tr.getName().getText()).add(t);
		                					
		                					if(loc_arcs.containsKey(tr.getName().getText()))
		                					{
		                						loc_arcs.get(tr.getName().getText()).add(s);
		                					}
		                					else
		                					{
		                						List<ArcHLAPI> a = new ArrayList<>();
		    		                				a.add(s);
		    		                				loc_arcs.put(tr.getName().getText(), a);	
		                					}	
		                					if(k==1)
		                						loc_arcs.get(tr.getName().getText()).add(arc);  //add once only
		                					if(k==plSrc.size())  //add this place to this location when all incoming transitions are also in this same location
		                					{
		                						if(loc_places.containsKey(tr.getName().getText()))
			                					{
			                						loc_places.get(tr.getName().getText()).add(pl);
			                					}
			                					else
			                					{
			                						List<PlaceHLAPI> pa = new ArrayList<>();
			    		                				pa.add(pl);
			    		                				loc_places.put(tr.getName().getText(), pa);	
			                					}
		                					}		                					
		                				}
		                				
		                				//Need to split the place if k < plSrc.size()
		                				
		                				else 
		                				{
		                					//connected transition is in different location
		                					
		                					
		                					
		                				}
		                			}
		                		}
		                	}
	                }
	                
	                
	                for(ArcHLAPI arc: arcs)
		        		{
		        			NodeHLAPI nd = arc.getSourceHLAPI();  //could be transition or place
		        			System.out.println(nd.getId());
		        			if(nd instanceof PlaceHLAPI)  //source is a place
						{
		        				pToT.put(nd.getId(), arc.getTargetHLAPI().getId());
						}
		        			if(nd instanceof TransitionHLAPI)  //source is a transition
						{
		        				tToP.put(nd.getId(), arc.getTargetHLAPI().getId());
						}
		        		}
	                System.out.println(pToT);
	                System.out.println(tToP);
	                for(PlaceHLAPI pl: places)
	                {
	                		JSONObject jo = new JSONObject();
	                		String id = pl.getId();
	                		id = id.substring(1);   //strip P
	                		jo.put("id", id);
	                		
	                		JSONArray wire1 = new JSONArray();
	                		jo.put("wires", wire1);
	                			                		
	                		ret.put(jo);
	                		
	                		String t = pToT.get(pl.getId());
	                		JSONArray warr = new JSONArray();
	                		
	                		if(t != null)
	                		{
	                			String tgtPl = tToP.get(t);                			
	                			if(tgtPl != null)  //can not be null
	                			{
	                				//wires json array and add target node                				
	                				warr.put(tgtPl.substring(1));                				
	                			}                			
	                		}
	                		else
	                		{
	                			
	                		}
	                		JSONArray w = (JSONArray)jo.get("wires");
	        				w.put(warr);
	                }
	                
	            }
                System.out.println("NR Json "+ret.toString(4));
                
        } catch (Exception e) {
                e.printStackTrace();
        }
        
	}
	
	

}
