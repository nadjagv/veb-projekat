package repositories;

import java.util.ArrayList;

import domain.Manifestacija;

public class ManifestacijaRepository {
	private ArrayList<Manifestacija> manifestacije;

	public ManifestacijaRepository() {
		manifestacije = new ArrayList<Manifestacija>();
	}
	
	

	public ManifestacijaRepository(ArrayList<Manifestacija> manifestacije) {
		super();
		this.manifestacije = manifestacije;
	}



	public ArrayList<Manifestacija> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(ArrayList<Manifestacija> manifestacije) {
		this.manifestacije = manifestacije;
	}
	
	public Manifestacija getOneById(String id) {
		return manifestacije.stream()
				.filter(manifestacija -> manifestacija.getId().contentEquals(id))
				.findAny()
				.orElse(null);
	}

}
