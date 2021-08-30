package domain;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Pol;
import enums.Uloga;

public class Prodavac extends Korisnik {
	private ArrayList<String> manifestacijeIds;
	private boolean blokiran;

	public Prodavac() {
		// TODO Auto-generated constructor stub
		manifestacijeIds = new ArrayList<String>();
	}

	public Prodavac(String username, String password, String ime, String prezime, Pol pol, LocalDate datumRodjenja,
			Uloga uloga, boolean obrisan, boolean blokiran) {
		super(username, password, ime, prezime, pol, datumRodjenja, uloga, obrisan);
		// TODO Auto-generated constructor stub
		manifestacijeIds = new ArrayList<String>();
		this.blokiran = blokiran;
	}

	public ArrayList<String> getManifestacijeIds() {
		return manifestacijeIds;
	}

	public void setManifestacijeIds(ArrayList<String> manifestacijeIds) {
		this.manifestacijeIds = manifestacijeIds;
	}

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

	

	
	

}
