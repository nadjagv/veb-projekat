package service;

import java.util.ArrayList;

import domain.Karta;
import repositories.KartaRepository;

public class KartaService {

	KartaRepository kartaRep;

	public KartaService() {
		kartaRep = KartaRepository.getInstance();
	}
	
	public ArrayList<Karta> preuzmiSve() {
		return kartaRep.getKarte();
	}
	
	public Karta preuzmiPoId(String id) {
		return kartaRep.getOneById(id);
	}

}
