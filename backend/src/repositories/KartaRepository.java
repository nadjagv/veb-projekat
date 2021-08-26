package repositories;

import java.util.ArrayList;

import domain.Karta;

public class KartaRepository {
	private ArrayList<Karta> karte;

	public KartaRepository() {
		karte = new ArrayList<Karta>();
	}
	

	public KartaRepository(ArrayList<Karta> karte) {
		super();
		this.karte = karte;
	}


	public ArrayList<Karta> getKarte() {
		return karte;
	}

	public void setKarte(ArrayList<Karta> karte) {
		this.karte = karte;
	}
	
	public Karta getOneById(String id) {
		return karte.stream()
				.filter(karta -> karta.getId().contentEquals(id))
				.findAny()
				.orElse(null);
	}

}
