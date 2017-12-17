package com.ibm.petrinets.labeledpn.hlapi;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.eclipse.emf.common.util.DiagnosticChain;

import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.IdRefLinker;
import fr.lip6.move.pnml.framework.utils.exception.InnerBuildException;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.pnmlcoremodel.PetriNet;
import fr.lip6.move.pnml.pnmlcoremodel.PetriNetDoc;

public class PetriNetDocHLAPI implements HLAPIRootClass//extends fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI  //implements HLAPIRootClass //
{
	
	/**
	 * The contained LLAPI element.
	 */
	private PetriNetDoc item;
		
	public PetriNetDocHLAPI(PetriNetDoc lowLevelAPI){
		item = lowLevelAPI;
	}
	
	/**
	 * This accessor automatically encapsulate all elements of the selected sublist.
	 * WARNING : this can creates a lot of new object in memory.
	 */
	
		public List<PetriNetHLAPI> getNetsHLAPI()
		{
			List<PetriNetHLAPI> retour = new ArrayList<PetriNetHLAPI>();
			for (PetriNet elemnt : getNets()) 
			{
				retour.add(new PetriNetHLAPI(elemnt));
			}
			return retour;
		}

	@Override
	public void fromPNML(OMElement arg0, IdRefLinker arg1)
			throws InnerBuildException, InvalidIDException, VoidRepositoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getContainedItem() {
		// TODO Auto-generated method stub
		return null;
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
	
	/**
	 * Return the encapsulate Low Level API object.
	 */
	public List<PetriNet> getNets(){
		return item.getNets();
	}

	//equals method
	public boolean equals(PetriNetDocHLAPI item){
		return item.getContainedItem().equals(getContainedItem());
	}

	public void addNetsHLAPI(PetriNetHLAPI unit){
		
		item.getNets().add((PetriNet)unit.getContainedItem());
	}

	public void removeNetsHLAPI(PetriNetHLAPI unit){
		item.getNets().remove((PetriNet)unit.getContainedItem());
	}
	
}
