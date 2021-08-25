package domain;

import java.awt.Image;
import java.time.LocalDateTime;

import enums.StatusManifestacije;
import enums.TipManifestacije;

public class Manifestacija {
	private String naziv;
	private TipManifestacije tip;
	private int brojMesta;
	private LocalDateTime datumVremeOdrzavanja;
	private double cenaRegular;
	private StatusManifestacije status;
	private Lokacija lokacija;
	private Image slika; // ili string putanja do slike
	private String slikaPath;
	

	public Manifestacija() {
		// TODO Auto-generated constructor stub
	}


	public Manifestacija(String naziv, TipManifestacije tip, int brojMesta, LocalDateTime datumVremeOdrzavanja,
			double cenaRegular, StatusManifestacije status, Lokacija lokacija, Image slika, String slikaPath) {
		super();
		this.naziv = naziv;
		this.tip = tip;
		this.brojMesta = brojMesta;
		this.datumVremeOdrzavanja = datumVremeOdrzavanja;
		this.cenaRegular = cenaRegular;
		this.status = status;
		this.lokacija = lokacija;
		this.slika = slika;
		this.slikaPath = slikaPath;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public TipManifestacije getTip() {
		return tip;
	}


	public void setTip(TipManifestacije tip) {
		this.tip = tip;
	}


	public int getBrojMesta() {
		return brojMesta;
	}


	public void setBrojMesta(int brojMesta) {
		this.brojMesta = brojMesta;
	}


	public LocalDateTime getDatumVremeOdrzavanja() {
		return datumVremeOdrzavanja;
	}


	public void setDatumVremeOdrzavanja(LocalDateTime datumVremeOdrzavanja) {
		this.datumVremeOdrzavanja = datumVremeOdrzavanja;
	}


	public double getCenaRegular() {
		return cenaRegular;
	}


	public void setCenaRegular(double cenaRegular) {
		this.cenaRegular = cenaRegular;
	}


	public StatusManifestacije getStatus() {
		return status;
	}


	public void setStatus(StatusManifestacije status) {
		this.status = status;
	}


	public Lokacija getLokacija() {
		return lokacija;
	}


	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}


	public Image getSlika() {
		return slika;
	}


	public void setSlika(Image slika) {
		this.slika = slika;
	}


	public String getSlikaPath() {
		return slikaPath;
	}


	public void setSlikaPath(String slikaPath) {
		this.slikaPath = slikaPath;
	}
	
	

}
