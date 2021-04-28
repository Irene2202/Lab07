package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	private List<PowerOutages> soluzioneMigliore;
	private List<PowerOutages> partenza;
	private int numPersoneMigliore;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutages> getWorstCase(Nerc n, int anniMax, int oreMax){
		soluzioneMigliore=new ArrayList<>();
		partenza=podao.getPowerOutages(n);
		List<PowerOutages> parziale=new ArrayList<>();
		
		numPersoneMigliore=0;
		
		cerca(parziale, anniMax, oreMax, 0);
		
		return soluzioneMigliore;
	}
	
	private void cerca(List<PowerOutages> parziale, int anniMax, int oreMax, int livello) {
		if(calcolaDurataAnni(parziale)>anniMax) {
			return;
		}
		else if (calcolaDurataOre(parziale)>oreMax) {
			return;
		}
		else if (parziale.size()>partenza.size()) {
			return;
		}
		
		int numPersone= calcolaNumeroPersone(parziale);
		if(soluzioneMigliore.size()==0 || numPersone>numPersoneMigliore) {
			soluzioneMigliore=new ArrayList<>(parziale);
			numPersoneMigliore=numPersone;
		}
		
		for(PowerOutages p: partenza) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				cerca(parziale, anniMax, oreMax, livello+1);
				parziale.remove(p);
			}
		}
		
			
	}

	public int calcolaNumeroPersone(List<PowerOutages> parziale) {
		int numPersone=0;
		for(PowerOutages p:parziale) {
			numPersone+=p.getCustomers_affected();
		}
		return numPersone;
	}

	public long calcolaDurataOre(List<PowerOutages> parziale) {
		long durataTot=0;
		for(PowerOutages p:parziale) {
			durataTot+=p.getDuration();
		}
		return durataTot;
	}

	private int calcolaDurataAnni(List<PowerOutages> parziale) {
		//Anno dell'ultimo elemento della lista(il più recente) meno quello del primo elemento(il più vecchio) 
		int durata=0;
		if(parziale.size()>0)
			durata=parziale.get(parziale.size()-1).getYear()-parziale.get(0).getYear();
		return durata;
	}
	
}
