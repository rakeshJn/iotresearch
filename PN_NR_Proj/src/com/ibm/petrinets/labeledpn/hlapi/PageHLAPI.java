package com.ibm.petrinets.labeledpn.hlapi;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.eclipse.emf.common.util.DiagnosticChain;

import fr.lip6.move.pnml.framework.hlapi.HLAPIClass;
import fr.lip6.move.pnml.framework.utils.IdRefLinker;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InnerBuildException;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.OtherException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.pnmlcoremodel.Name;
import fr.lip6.move.pnml.pnmlcoremodel.NodeGraphics;
import fr.lip6.move.pnml.pnmlcoremodel.Page;
import fr.lip6.move.pnml.pnmlcoremodel.PnObject;
import fr.lip6.move.pnml.pnmlcoremodel.ToolInfo;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.NameHLAPI;
import fr.lip6.move.pnml.pnmlcoremodel.hlapi.ToolInfoHLAPI;

public class PageHLAPI implements HLAPIClass
{
	
	/**
	 * The contained LLAPI element.
	 */
	private Page item;
	
	
	public PageHLAPI(Page lowLevelAPI) {
		item = lowLevelAPI;
		
	}
	
	
	/**
	 * This accessor return a list of encapsulated subelement, only of PlaceHLAPI kind.
	 * WARNING : this method can creates a lot of new object in memory.
	 */
	public List<PlaceHLAPI> getObjects_PlaceHLAPI(){
		List<PlaceHLAPI> retour = new ArrayList<PlaceHLAPI>();
		for (PnObject elemnt : getObjects()) {
			if(elemnt.getClass().equals(fr.lip6.move.pnml.pnmlcoremodel.impl.PlaceImpl.class)){
				retour.add(new PlaceHLAPI((fr.lip6.move.pnml.pnmlcoremodel.Place)elemnt));
			}
		}
		return retour;
	}

	public java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.ArcHLAPI> getObjects_ArcHLAPI(){
		java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.ArcHLAPI> retour = new ArrayList<fr.lip6.move.pnml.pnmlcoremodel.hlapi.ArcHLAPI>();
		for (PnObject elemnt : getObjects()) {
			if(elemnt.getClass().equals(fr.lip6.move.pnml.pnmlcoremodel.impl.ArcImpl.class)){
				retour.add(new fr.lip6.move.pnml.pnmlcoremodel.hlapi.ArcHLAPI(
					(fr.lip6.move.pnml.pnmlcoremodel.Arc)elemnt
					));
			}
		}
		return retour;
	}
	
	public java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI> getObjects_PageHLAPI(){
		java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI> retour = new ArrayList<fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI>();
		for (PnObject elemnt : getObjects()) {
			if(elemnt.getClass().equals(fr.lip6.move.pnml.pnmlcoremodel.impl.PageImpl.class)){
				retour.add(new fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI(
					(fr.lip6.move.pnml.pnmlcoremodel.Page)elemnt
					));
			}
		}
		return retour;
	}
	
	public java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.RefTransitionHLAPI> getObjects_RefTransitionHLAPI(){
		java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.RefTransitionHLAPI> retour = new ArrayList<fr.lip6.move.pnml.pnmlcoremodel.hlapi.RefTransitionHLAPI>();
		for (PnObject elemnt : getObjects()) {
			if(elemnt.getClass().equals(fr.lip6.move.pnml.pnmlcoremodel.impl.RefTransitionImpl.class)){
				retour.add(new fr.lip6.move.pnml.pnmlcoremodel.hlapi.RefTransitionHLAPI(
					(fr.lip6.move.pnml.pnmlcoremodel.RefTransition)elemnt
					));
			}
		}
		return retour;
	}
	
	public java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.TransitionHLAPI> getObjects_TransitionHLAPI(){
		java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.TransitionHLAPI> retour = new ArrayList<fr.lip6.move.pnml.pnmlcoremodel.hlapi.TransitionHLAPI>();
		for (PnObject elemnt : getObjects()) {
			if(elemnt.getClass().equals(fr.lip6.move.pnml.pnmlcoremodel.impl.TransitionImpl.class)){
				retour.add(new fr.lip6.move.pnml.pnmlcoremodel.hlapi.TransitionHLAPI(
					(fr.lip6.move.pnml.pnmlcoremodel.Transition)elemnt
					));
			}
		}
		return retour;
	}
	
	
	/**
	 * This accessor return a list of encapsulated subelement, only of RefPlaceHLAPI kind.
	 * WARNING : this method can creates a lot of new object in memory.
	 */
	public java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.RefPlaceHLAPI> getObjects_RefPlaceHLAPI(){
		java.util.List<fr.lip6.move.pnml.pnmlcoremodel.hlapi.RefPlaceHLAPI> retour = new ArrayList<fr.lip6.move.pnml.pnmlcoremodel.hlapi.RefPlaceHLAPI>();
		for (PnObject elemnt : getObjects()) {
			if(elemnt.getClass().equals(fr.lip6.move.pnml.pnmlcoremodel.impl.RefPlaceImpl.class)){
				retour.add(new fr.lip6.move.pnml.pnmlcoremodel.hlapi.RefPlaceHLAPI(
					(fr.lip6.move.pnml.pnmlcoremodel.RefPlace)elemnt
					));
			}
		}
		return retour;
	}
	
	public void addToolspecificsHLAPI(ToolInfoHLAPI unit) {
		item.getToolspecifics().add((ToolInfo)unit.getContainedItem());		
	}


	
	public Page getContainerPage() {
		return item.getContainerPage();
	}


	
	public PageHLAPI getContainerPageHLAPI() {
		if(item.getContainerPage() == null) return null;
		return new PageHLAPI(item.getContainerPage());
	}


	
	public String getId() {
		return item.getId();
	}


	
	public Name getName() {
		return item.getName();
	}


	
	public NameHLAPI getNameHLAPI() {
		if(item.getName() == null) return null;
		return new NameHLAPI(item.getName());
	}


	
	public List<ToolInfo> getToolspecifics() {
		return item.getToolspecifics();
	}


	public List<ToolInfoHLAPI> getToolspecificsHLAPI() {
		List<ToolInfoHLAPI> retour = new ArrayList<ToolInfoHLAPI>();
		for (ToolInfo elemnt : getToolspecifics()) {
			retour.add(new ToolInfoHLAPI(elemnt));
		}
		return retour;
	}


	
	public void removeToolspecificsHLAPI(ToolInfoHLAPI unit) {
		item.getToolspecifics().remove((ToolInfo)unit.getContainedItem());
	}


	
	public void setContainerPageHLAPI(fr.lip6.move.pnml.pnmlcoremodel.hlapi.PageHLAPI elem) {
		if(elem!=null)
			item.setContainerPage((Page)elem.getContainedItem());
	}


	
	public void setIdHLAPI(String elem) throws InvalidIDException, VoidRepositoryException {
		if(elem!=null){
			
			try{
			item.setId(ModelRepository.getInstance().getCurrentIdRepository().changeId(this, elem));
			}catch (OtherException e){
			ModelRepository.getInstance().getCurrentIdRepository().checkId(elem, this);
			}
		}
	}


	
	public void setNameHLAPI(NameHLAPI elem) {
		if(elem!=null)
			item.setNodegraphics((NodeGraphics)elem.getContainedItem());
	}


	@Override
	public void fromPNML(OMElement subRoot, IdRefLinker idr)
			throws InnerBuildException, InvalidIDException, VoidRepositoryException {
		item.fromPNML(subRoot,idr);
	}


	@Override
	public Object getContainedItem() {
		return item;
	}


	@Override
	public String toPNML() {
		return item.toPNML();
	}


	@Override
	public void toPNML(FileChannel fc) {
		 item.toPNML(fc);
	}


	@Override
	public boolean validateOCL(DiagnosticChain diagnostics) {
		return item.validateOCL(diagnostics);

	}
	
	public List<PnObject> getObjects(){
		return item.getObjects();
	}
}
