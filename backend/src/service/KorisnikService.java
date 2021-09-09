package service;

import domain.Korisnik;
import enums.Uloga;
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
	
	public Korisnik izmeniPodatke(Korisnik k) {
		Korisnik korisnik = pretraziPoUsername(k.getUsername());
		
		if (korisnik == null || korisnik.isObrisan())
			return null;
		
		korisnik.setIme(k.getIme());
		korisnik.setPrezime(k.getPrezime());
		korisnik.setPassword(k.getPassword());
		korisnik.setPol(k.getPol());
		korisnik.setDatumRodjenja(k.getDatumRodjenja());
		
		if (k.getUloga().equals(Uloga.ADMINISTRATOR)) {
			administratorRep.save();
			System.out.println("lol");
		}else if (k.getUloga().equals(Uloga.KUPAC)) {
			kupacRep.save();
		}else {
			prodavacRep.save();
		}
		
		return korisnik;
	}
	
	

}
