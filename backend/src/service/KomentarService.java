package service;

import java.util.ArrayList;

import domain.Komentar;
import repositories.KomentarRepository;

public class KomentarService {

	KomentarRepository komentarRep;

	public KomentarService() {
		komentarRep = KomentarRepository.getInstance();
	}
	
	public ArrayList<Komentar> preuzmiSve() {
		return komentarRep.getKomentari();
	}
	
	public Komentar preuzmiPoId(String id) {
		return komentarRep.getOneById(id);
	}

}
