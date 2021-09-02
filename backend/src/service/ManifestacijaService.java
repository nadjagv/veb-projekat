package service;

import java.util.ArrayList;

import domain.Manifestacija;
import repositories.ManifestacijaRepository;

public class ManifestacijaService {
	ManifestacijaRepository manifestacijaRep;

	public ManifestacijaService() {
		manifestacijaRep = ManifestacijaRepository.getInstance();
	}
	
	public ArrayList<Manifestacija> preuzmiSve() {
		return manifestacijaRep.getManifestacije();
	}
	
	public Manifestacija preuzmiPoId(String id) {
		return manifestacijaRep.getOneById(id);
	}

}
