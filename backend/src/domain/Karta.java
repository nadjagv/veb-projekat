package domain;

import java.time.LocalDateTime;

import enums.StatusKarte;
import enums.TipKarte;

public class Karta {
	private String id;
	private Manifestacija manifestacija;
	private LocalDateTime datumVremeOdrzavanja; //redundantno
	private double cena;
	private Kupac kupac;
	private StatusKarte status;
	private TipKarte tip;

	public Karta() {
		// TODO Auto-generated constructor stub
	}

	public Karta(String id, Manifestacija manifestacija, LocalDateTime datumVremeOdrzavanja, double cena, Kupac kupac,
			StatusKarte status, TipKarte tip) {
		super();
		this.id = id;
		this.manifestacija = manifestacija;
		this.datumVremeOdrzavanja = datumVremeOdrzavanja;
		this.cena = cena;
		this.kupac = kupac;
		this.status = status;
		this.tip = tip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Manifestacija getManifestacija() {
		return manifestacija;
	}

	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}

	public LocalDateTime getDatumVremeOdrzavanja() {
		return datumVremeOdrzavanja;
	}

	public void setDatumVremeOdrzavanja(LocalDateTime datumVremeOdrzavanja) {
		this.datumVremeOdrzavanja = datumVremeOdrzavanja;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public StatusKarte getStatus() {
		return status;
	}

	public void setStatus(StatusKarte status) {
		this.status = status;
	}

	public TipKarte getTip() {
		return tip;
	}

	public void setTip(TipKarte tip) {
		this.tip = tip;
	}
	

}
