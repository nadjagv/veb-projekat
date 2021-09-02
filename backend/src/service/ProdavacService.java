package service;

import java.util.ArrayList;

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

}
