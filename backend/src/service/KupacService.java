package service;

import java.util.ArrayList;

import domain.Korisnik;
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
	
	public Kupac registruj(Korisnik k) {
		Kupac p = new Kupac(k.getUsername(), k.getPassword(), k.getIme(), k.getPrezime(), k.getPol(),k.getDatumRodjenja(), k.getUloga(), false, false);
		kupacRep.add(p);
		return p;
	}

}
