package domain;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Pol;
import enums.Uloga;

public class Prodavac extends Korisnik {
	private ArrayList<Manifestacija> manifestacije;

	public Prodavac() {
		// TODO Auto-generated constructor stub
		manifestacije = new ArrayList<Manifestacija>();
	}

	public Prodavac(String username, String password, String ime, String prezime, Pol pol, LocalDate datumRodjenja,
			Uloga uloga) {
		super(username, password, ime, prezime, pol, datumRodjenja, uloga);
		// TODO Auto-generated constructor stub
		manifestacije = new ArrayList<Manifestacija>();
	}

	public ArrayList<Manifestacija> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(ArrayList<Manifestacija> manifestacije) {
		this.manifestacije = manifestacije;
	}
	
	

}
