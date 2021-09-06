package service;

import domain.Korisnik;
import helperClasses.Kredencijali;
import repositories.AdministratorRepository;
import repositories.KupacRepository;
import repositories.ProdavacRepository;
import utils.TokenUtils;

public class KorisnikService {
	
	AdministratorRepository administratorRep;
	KupacRepository kupacRep;
	ProdavacRepository prodavacRep;

	public KorisnikService() {
		administratorRep = AdministratorRepository.getInstance();
		kupacRep = KupacRepository.getInstance();
		prodavacRep = ProdavacRepository.getInstance();
	}
	
	public Korisnik pretraziPoUsername(String username) {
		Korisnik pronadjen;
		pronadjen = kupacRep.getOneByUsername(username);
		if (pronadjen != null) {
			return pronadjen;
		}
		pronadjen = prodavacRep.getOneByUsername(username);
		if (pronadjen != null) {
			return pronadjen;
		}
		pronadjen = administratorRep.getOneByUsername(username);
		if (pronadjen != null) {
			return pronadjen;
		}
		
		System.out.println(pronadjen.getUsername());
		
		return null;
	}
	
	public Korisnik logIn(Kredencijali kred) {
		Korisnik korisnik = pretraziPoUsername(kred.getUsername());
		if (korisnik == null || korisnik.isObrisan())
			return null;
		if (korisnik.getPassword().equals(kred.getPassword())) {
			TokenUtils.napraviToken(korisnik);
			return korisnik;
		}
		return null;
	}

}
