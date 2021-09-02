package service;

import java.util.ArrayList;

import domain.Kupac;
import repositories.KupacRepository;

public class KupacService {
	
	KupacRepository kupacRep;

	public KupacService() {
		kupacRep = KupacRepository.getInstance();
	}
	
	public ArrayList<Kupac> preuzmiSve() {
		return kupacRep.getKupci();
	}
	
	public Kupac preuzmiPoUsername(String username) {
		return kupacRep.getOneByUsername(username);
	}

}
