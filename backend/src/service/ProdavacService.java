package service;

import java.util.ArrayList;

import domain.Korisnik;
import domain.Prodavac;
import repositories.ProdavacRepository;

public class ProdavacService {

	ProdavacRepository prodavacRep;

	public ProdavacService() {
		prodavacRep = ProdavacRepository.getInstance();
	}
	
	public ArrayList<Prodavac> preuzmiSve() {
		return prodavacRep.getProdavci();
	}
	
	public Prodavac preuzmiPoUsername(String username) {
		return prodavacRep.getOneByUsername(username);
	}
	
	public Prodavac registruj(Korisnik k) {
		Prodavac p = new Prodavac(k.getUsername(), k.getPassword(), k.getIme(), k.getPrezime(), k.getPol(),k.getDatumRodjenja(), k.getUloga(), false, false);
		prodavacRep.add(p);
		return p;
	}

}
