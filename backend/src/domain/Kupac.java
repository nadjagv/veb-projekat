package domain;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.ImeTipaKupca;
import enums.Pol;
import enums.Uloga;

public class Kupac extends Korisnik {
	
	private ArrayList<String> karteIds;
	private ArrayList<Otkazivanje> otkazivanja;
	private double brojBodova;
	private ImeTipaKupca tip;
	private boolean blokiran;

	public Kupac() {
		karteIds = new ArrayList<String>();
		brojBodova = 0;
		tip = ImeTipaKupca.BRONZANI;
	}

	public Kupac(String username, String password, String ime, String prezime, Pol pol, LocalDate datumRodjenja,
			Uloga uloga, boolean obrisan, boolean blokiran) {
		super(username, password, ime, prezime, pol, datumRodjenja, uloga, obrisan);
		karteIds = new ArrayList<String>();
		brojBodova = 0;
		tip = ImeTipaKupca.BRONZANI;
		this.blokiran = blokiran;
		
	}

	

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

	public double getBrojBodova() {
		return brojBodova;
	}

	public void setBrojBodova(double brojBodova) {
		this.brojBodova = brojBodova;
	}

	public ArrayList<String> getKarteIds() {
		return karteIds;
	}

	public void setKarteIds(ArrayList<String> karteIds) {
		this.karteIds = karteIds;
	}

	public ImeTipaKupca getTip() {
		return tip;
	}

	public void setTip(ImeTipaKupca tip) {
		this.tip = tip;
	}

	public ArrayList<Otkazivanje> getOtkazivanja() {
		return otkazivanja;
	}

	public void setOtkazivanja(ArrayList<Otkazivanje> otkazivanja) {
		this.otkazivanja = otkazivanja;
	}

	
	
	

}
