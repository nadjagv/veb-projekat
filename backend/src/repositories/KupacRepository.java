package repositories;

import java.util.ArrayList;

import domain.Kupac;

public class KupacRepository {
	private ArrayList<Kupac> kupci;

	public KupacRepository() {
		kupci = new ArrayList<Kupac>();
	}

	public KupacRepository(ArrayList<Kupac> kupci) {
		super();
		this.kupci = kupci;
	}

	public ArrayList<Kupac> getKupci() {
		return kupci;
	}

	public void setKupci(ArrayList<Kupac> kupci) {
		this.kupci = kupci;
	}
	
	public Kupac getOneByUsername(String username) {
		return kupci.stream()
				.filter(kupac -> kupac.getUsername().contentEquals(username))
				.findAny()
				.orElse(null);
	}

}
