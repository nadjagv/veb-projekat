package domain;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Pol;
import enums.Uloga;

public class Kupac extends Korisnik {
	
	private ArrayList<Karta> karte;
	private double brojBodova;
	private TipKupca tip;

	public Kupac() {
		karte = new ArrayList<Karta>();
		brojBodova = 0;
		// TODO Auto-generated constructor stub
	}

	public Kupac(String username, String password, String ime, String prezime, Pol pol, LocalDate datumRodjenja,
			Uloga uloga) {
		super(username, password, ime, prezime, pol, datumRodjenja, uloga);
		karte = new ArrayList<Karta>();
		brojBodova = 0;
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Karta> getKarte() {
		return karte;
	}

	public void setKarte(ArrayList<Karta> karte) {
		this.karte = karte;
	}

	public double getBrojBodova() {
		return brojBodova;
	}

	public void setBrojBodova(double brojBodova) {
		this.brojBodova = brojBodova;
	}

	public TipKupca getTip() {
		return tip;
	}

	public void setTip(TipKupca tip) {
		this.tip = tip;
	}
	
	

}
