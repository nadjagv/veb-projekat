package repositories;

import java.util.ArrayList;

import domain.Komentar;

public class KomentarRepository {
	private ArrayList<Komentar> komentari;

	public KomentarRepository() {
		komentari = new ArrayList<Komentar>();
	}

	public KomentarRepository(ArrayList<Komentar> komentari) {
		super();
		this.komentari = komentari;
	}

	public ArrayList<Komentar> getKomentari() {
		return komentari;
	}

	public void setKomentari(ArrayList<Komentar> komentari) {
		this.komentari = komentari;
	}
	
	public Komentar getOneById(String id) {
		return komentari.stream()
				.filter(komentar -> komentar.getId().contentEquals(id))
				.findAny()
				.orElse(null);
	}

}
