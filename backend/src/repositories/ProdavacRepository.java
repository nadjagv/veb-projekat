package repositories;

import java.util.ArrayList;

import domain.Prodavac;

public class ProdavacRepository {
	private ArrayList<Prodavac> prodavci;

	public ProdavacRepository() {
		prodavci = new ArrayList<Prodavac>();
	}

	public ProdavacRepository(ArrayList<Prodavac> prodavci) {
		super();
		this.prodavci = prodavci;
	}

	public ArrayList<Prodavac> getProdavci() {
		return prodavci;
	}

	public void setProdavci(ArrayList<Prodavac> prodavci) {
		this.prodavci = prodavci;
	}
	
	public Prodavac getOneByUsername(String username) {
		return prodavci.stream()
				.filter(prodavac -> prodavac.getUsername().contentEquals(username))
				.findAny()
				.orElse(null);
	}

}
