package com.ibm.pn.nr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;

import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.PNMLUtils;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.ArcHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.NameHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.NodeHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PNTypeHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.PlaceHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.TransitionHLAPI;

public class PNToDPN {

	public static void main(String[] args) {
		String path = "/Users/rakesh/eclipse-workspace2/PN_NR_Proj/Ref/data5.pnml";
		
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
	                    
	                List<PlaceHLAPI> 		places = page.getObjects_PlaceHLAPI();
	                List<TransitionHLAPI> 	transitions = page.getObjects_TransitionHLAPI();
	                List<ArcHLAPI> 			arcs = page.getObjects_ArcHLAPI();
	                
	                //Map<PlaceHLAPI, List<PlaceHLAPI>> placeToPlaces = new HashMap<>();
	                
//	                Map<String, String> pToT = new HashMap<>();  //place to transition map
//	                Map<String, String> tToP = new HashMap<>();  //transition to place map
	                
	                Map<String, List<TransitionHLAPI>> 	loc_new_trans = new HashMap<>(); //contains only newly generated transitions which represent mqtt.in/.out
	                Map<String, List<PlaceHLAPI>> 		loc_places = new HashMap<>();
//	                Map<String, List<ArcHLAPI>> 			loc_arcs = new HashMap<>();
	                
//	                for(ArcHLAPI arc: arcs)
//	        			{
//		        			NodeHLAPI nd = arc.getSourceHLAPI();  //could be transition or place
//		        			System.out.println(nd.getId());
//		        			if(nd instanceof PlaceHLAPI)  //source is a place
//						{
//		        				pToT.put(nd.getId(), arc.getTargetHLAPI().getId());
//						}
//		        			if(nd instanceof TransitionHLAPI)  //source is a transition
//						{
//		        				tToP.put(nd.getId(), arc.getTargetHLAPI().getId());
//						}
//	        			}
//	                System.out.println(pToT);
//	                System.out.println(tToP);
	                System.out.println("Total places "+places.size());
	                for(PlaceHLAPI pl: places)
	                {
	                		List<ArcHLAPI> inarcs = pl.getInArcsHLAPI();
	                		List<ArcHLAPI> outarcs = pl.getOutArcsHLAPI();
	                		Set<String> inLocations = new HashSet<>();
	                		Set<String> outLocations = new HashSet<>();
	                		Set<String> locationsInvolved = new HashSet<>();
	                		
	                		System.out.println("Total inarcs "+inarcs.size());
	                		System.out.println("Total outarcs "+outarcs.size());
	                		
	                		for(ArcHLAPI inarc: inarcs)
	                		{
	                			TransitionHLAPI src = (TransitionHLAPI)inarc.getSourceHLAPI();
	                			if(src.getNameHLAPI() != null)
	                				inLocations.add(src.getNameHLAPI().getText());
	                		}
	                		for(ArcHLAPI outarc: outarcs)
	                		{
	                			TransitionHLAPI tgt = (TransitionHLAPI)outarc.getTargetHLAPI();
	                			if(tgt.getNameHLAPI() != null)
	                			{
	                				String ln = tgt.getNameHLAPI().getText();
	                				if(!inLocations.contains(ln))
	                					outLocations.add(ln);
	                			}
	                		}
	                		locationsInvolved.addAll(inLocations);
	                		locationsInvolved.addAll(outLocations);
	                		System.out.println("Total locations "+locationsInvolved.size());
	                		if(locationsInvolved.size()==1)//inLocations.size() ==1 && outLocations.size()==1 && inLocations.iterator().next().equals(outLocations.iterator().next()))
	                		{	                			
	                			//place doesn't need split. its location is same as inLocation
	                			
	                			//all inarcs also belong to this location
	                			//place and all output arcs belong to this same location
	                			//continue;
	                			String loc = locationsInvolved.iterator().next();
	                			System.out.println("Only Location "+loc);
	                			populateMap(loc_places, loc, pl);  //arcs are with the place                			
	                			
	                		}
	                		else if(locationsInvolved.size() > 1)//if(inLocations.size()== 1 && outLocations.size()==1) 
	                		{
	                			//input and output transition locations are different (could be single) locations, needs split
	                			//create a copy for each input location and each output location if it is diff from input
	                			for(String loc: locationsInvolved)
	                			{
	                				//PlaceHLAPI newpl = new PlaceHLAPI(pl.getId()+"_"+loc);
	                				//take all in arcs from same location as is
	                				//take remote in arcs with special handling
	                				//take all out arcs from same location as is
	                				//take remote arcs with special handling??
	                			}
	                			for(String loc: inLocations) 
	                			{
	                				//for all in locations, we generate a new place
	                				PlaceHLAPI newpl = new PlaceHLAPI(pl.getId()+"_"+loc);
	                				System.out.println(pl.getInArcsHLAPI().get(0).getSourceHLAPI());
	                				newpl.getInArcsHLAPI().addAll(getArcsFromOriginLocation(pl.getInArcsHLAPI(), loc, newpl));  //add all local (only) in arcs
	                				newpl.getOutArcsHLAPI().addAll(getArcsFromTargetLocation(pl.getOutArcsHLAPI(), loc, newpl)); //add all local (only) out arcs
	                				
	                				//add to loc:PL map
	                				populateMap(loc_places, loc, newpl);
	                				
	                				//find remote output transitions and generate mqtt.out for each of them
	                				for(String outLoc: outLocations)
	                				{
	                					List<ArcHLAPI> arcsout = pl.getOutArcsHLAPI();
	                					//System.out.println("Total output arcs "+arcsout.size());
	                					List<TransitionHLAPI> remoteTrans = getTargetTransitionsAtLocation(arcsout, outLoc);
	                					int i=0;
	                					for(TransitionHLAPI tr: remoteTrans)
	                					{
	                						TransitionHLAPI tgn = new TransitionHLAPI("Tmqtt.out"+tr.getId()+"_"+i);  //add to loc_Tr map, name for channel??
	                						tgn.setNameHLAPI(new NameHLAPI(loc));   //the new place and transition will be at same location as new in Place
	                						//populateMap(loc_new_trans, loc, tgn);
	                						
	                						PlaceHLAPI pgn = new PlaceHLAPI("P"+loc+i);  //add to loc:pl map
	                						populateMap(loc_places, loc, pgn);
	                						
	                						//generate arcs to connect these all
	                						ArcHLAPI a1 = new ArcHLAPI("a1_"+loc+i, newpl, tgn);
	                						ArcHLAPI a2 = new ArcHLAPI("a2_"+loc+i, tgn, pgn);
	                						//ArcHLAPI a3 = new ArcHLAPI("a3_"+loc+i, pgn, tr);
	                						
	                						newpl.getOutArcsHLAPI().add(a1);
	                						tgn.getInArcsHLAPI().add(a1);
	                						tgn.getOutArcsHLAPI().add(a2);
	                						pgn.getInArcsHLAPI().add(a2);
	                						//pgn.getOutArcsHLAPI().add(a3);
	                						//tr.getInArcsHLAPI().add(a3);
	                						i++;
	                					}
	                				}
	                			}
	                			
	                			for(String loc: outLocations) 
	                			{
	                				System.out.println("Location is process = "+loc);
	                				System.out.println("Size of outarcs "+pl.getOutArcsHLAPI());
	                				
	                				//for all out locations, we generate a new place
	                				PlaceHLAPI newpl = new PlaceHLAPI(pl.getId()+"_"+loc);
	                				newpl.getInArcsHLAPI().addAll(getArcsFromOriginLocation(pl.getInArcsHLAPI(), loc, newpl));  //add all local (only) in arcs
	                				newpl.getOutArcsHLAPI().addAll(getArcsFromTargetLocation(pl.getOutArcsHLAPI(), loc, newpl));  //add all local (only) out arcs
	                	
	                				//add to loc:PL map	                				
	                				populateMap(loc_places, loc, newpl);
	                				
	                				//find remote input transitions and generate mqtt.in for each of them
	                				for(String inLoc: inLocations)
	                				{
	                					List<ArcHLAPI> arcsin = inarcs;//pl.getInArcsHLAPI();	 //this may give incorrect InArcs as page has been modified for original in arcs               					
	                					List<TransitionHLAPI> remoteTrans = getSourceTransitionsAtLocation(arcsin, inLoc);  //use cached inarcs
	                					int i=0;
	                					for(TransitionHLAPI tr: remoteTrans)
	                					{
	                						//generate new place and new transition representing mqtt.in in new channel
	                						PlaceHLAPI pcn = new PlaceHLAPI("P"+"_"+loc+"_"+i);  //add to loc:pl map	     
	                						populateMap(loc_places, loc, pcn);
	                						
	                						TransitionHLAPI tcn = new TransitionHLAPI("Tmqtt.in"+tr.getId()+"_"+i);  //add to loc_Tr map, name for channel??
	                						tcn.setNameHLAPI(new NameHLAPI(loc));   //the new place and transition will be at same location as new out Place
	                						//populateMap(loc_new_trans, loc, tcn);
	                						
	                						//generate arcs to connect these all
	                						//ArcHLAPI a1 = new ArcHLAPI("a1."+loc+i, tr, pcn);
	                						ArcHLAPI a2 = new ArcHLAPI("a2."+loc+i, pcn, tcn);
	                						ArcHLAPI a3 = new ArcHLAPI("a3."+loc+i, tcn, newpl);
	                						
	                						//tr.getOutArcsHLAPI().add(a1);
	                						//pcn.getInArcsHLAPI().add(a1);
	                						pcn.getOutArcsHLAPI().add(a2);	                						
	                						tcn.getInArcsHLAPI().add(a2);
	                						tcn.getOutArcsHLAPI().add(a3);
	                						newpl.getInArcsHLAPI().add(a3);
	                						i++;
	                					}
	                				}
	                			}
	                		}
	                		
	                }
	                
	                generatePNMLs(loc_places, loc_new_trans);    
                
	        } 
	                
        }catch (Exception e) 
   		{
            e.printStackTrace();
        }

	}
	
	//assumes source is a transition, returns all arcs which belong to given location
	private static Set<ArcHLAPI> getArcsFromOriginLocation(List<ArcHLAPI> inArcs, String location, PlaceHLAPI newPl) throws Exception
	{
		Set<ArcHLAPI> ret = new HashSet<>();
		for(ArcHLAPI arc: inArcs)
		{
			if(((fr.lip6.move.pnml.pnmlcoremodel.Transition)arc.getSource()).getName().getText().contentEquals(location))
			{
				ArcHLAPI a = new ArcHLAPI(arc.getId()+location, arc.getSourceHLAPI(), arc.getTargetHLAPI(), newPl.getContainerPageHLAPI());
				a.setTargetHLAPI(newPl);
				ret.add(a);
			}
		}
		return ret;
	}
	
	//assumes source is a place, returns all arcs which belong to given location
	//replace source with new place
	private static Set<ArcHLAPI> getArcsFromTargetLocation(List<ArcHLAPI> outArcs, String location, PlaceHLAPI newPl) throws Exception
	{
		Set<ArcHLAPI> ret = new HashSet<>();
		for(ArcHLAPI arc: outArcs)
		{
			if(((fr.lip6.move.pnml.pnmlcoremodel.Transition)arc.getTarget()).getName().getText().contentEquals(location))
			{
				ArcHLAPI a = new ArcHLAPI(arc.getId()+location, arc.getSourceHLAPI(), arc.getTargetHLAPI(), newPl.getContainerPageHLAPI());
				a.setSourceHLAPI(newPl);
				ret.add(a);
				System.out.println("Target = "+arc.getTargetHLAPI().getId());
			}
		}
		System.out.println("Returning getArcsFromTargetLocation "+ret.size());
		return ret;
	}
	
	private static List<TransitionHLAPI> getSourceTransitionsAtLocation(List<ArcHLAPI> arcsin, String loc)
	{
		List<TransitionHLAPI> ret = new ArrayList<>();
		for(ArcHLAPI arc: arcsin)
		{
			TransitionHLAPI tr = (TransitionHLAPI)arc.getSourceHLAPI();
			String locn = tr.getNameHLAPI().getText();
			System.out.println(locn);
			if( locn != null && locn.equals(loc))
				ret.add(tr);					
		}
		System.out.println("Source transitions at location "+loc+" are "+ret.size());
		return ret;
	}
	
	private static List<TransitionHLAPI> getTargetTransitionsAtLocation(List<ArcHLAPI> arcsout, String loc)
	{
		List<TransitionHLAPI> ret = new ArrayList<>();
		for(ArcHLAPI arc: arcsout)
		{
			TransitionHLAPI tr = (TransitionHLAPI)arc.getTargetHLAPI();
			String locn = tr.getNameHLAPI().getText();
			if( locn != null && locn.equals(loc))
				ret.add(tr);					
		}
		System.out.println("Target transitions at location "+loc+" are "+ret.size());
		return ret;
	}
	
	private static void populateMap(Map<String, List<PlaceHLAPI>> map, String key, PlaceHLAPI value)
	{
		List<PlaceHLAPI> v = map.get(key);
		if(v==null)
			v = new ArrayList<>();
		//System.out.println("Adding place "+value);
		v.add(value);
		map.put(key, v);
	}
	
	private static void populateMap(Map<String, List<TransitionHLAPI>> map, String key, TransitionHLAPI value)
	{
		List<TransitionHLAPI> v = map.get(key);
		if(v==null)
			v = new ArrayList<>();
		v.add(value);
		map.put(key, v);
	}
	
	private static void populateMap(Map<String, List<ArcHLAPI>> map, String key, ArcHLAPI value)
	{
		List<ArcHLAPI> v = map.get(key);
		if(v==null)
			v = new ArrayList<>();
		v.add(value);
		map.put(key, v);
	}
	
	private static void generatePNMLs(Map<String, List<PlaceHLAPI>> plMap, Map<String, List<TransitionHLAPI>> 	loc_new_trans) throws Exception
	{
		Set<String> locations = plMap.keySet();
		System.out.println("generatePNMLs "+locations.size());
		ModelRepository.getInstance().createDocumentWorkspace("void");
		ModelRepository.getInstance().setPrettyPrintStatus(true);
		Iterator<String> iter = locations.iterator();
		
		while(iter.hasNext())
		{
			String loc = iter.next();
			List<PlaceHLAPI> places = plMap.get(loc);
			
			generatePNML(places, loc);
			
		}
	}
	
	private static String generatePNML(List<PlaceHLAPI> places,  String location) throws Exception
	{
		String ret = "";
		
//		ModelRepository.getInstance().createDocumentWorkspace(location);
//		ModelRepository.getInstance().setPrettyPrintStatus(true);
		PetriNetDocHLAPI doc = new PetriNetDocHLAPI();
		PetriNetHLAPI net = new PetriNetHLAPI("net"+"."+location, PNTypeHLAPI.COREMODEL,new NameHLAPI("net"+"."+location), doc);
		PageHLAPI page = new PageHLAPI("toppage."+location, new NameHLAPI("myname"),null, net);
		
		for(PlaceHLAPI pl: places)
		{
			pl.setContainerPageHLAPI(page);
			setPageForArcs(pl.getInArcsHLAPI(), page);			
			setPageForArcs(pl.getOutArcsHLAPI(), page);			
		}		
		System.out.println(doc.toPNML());
		return ret;
	}
	
	private static void setPageForArcs(List<ArcHLAPI> arcs, PageHLAPI page)
	{
		for(ArcHLAPI a: arcs)
		{
			a.setContainerPageHLAPI(page);
			NodeHLAPI src = a.getSourceHLAPI();
			NodeHLAPI tgt = a.getTargetHLAPI();
			if(src != null) src.setContainerPageHLAPI(page);
			if(tgt != null) tgt.setContainerPageHLAPI(page);
		}
	}
	
	private static void setTransitionsPage(List<TransitionHLAPI> trans, PageHLAPI p)
	{
		for(TransitionHLAPI t: trans)
		{
			t.setContainerPageHLAPI(p);
		}
	}
	
	private static List<TransitionHLAPI> getTransitionsAtLocation(String loc, List<TransitionHLAPI> trans)
	{
		List<TransitionHLAPI> ret = new ArrayList<>();
		for(TransitionHLAPI t: trans)
		{
			if(t.getName()!=null && t.getName().getText().contentEquals(loc))
				ret.add(t);
		}
		return ret;
	}
	 /*
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
*/

}
