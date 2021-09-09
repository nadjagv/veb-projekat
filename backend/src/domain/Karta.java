package domain;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;

import enums.StatusKarte;
import enums.TipKarte;

public class Karta {
	private String id;
	private String manifestacijaId;
	private String nazivManifestacije;
	@JsonbDateFormat(JsonbDateFormat.TIME_IN_MILLIS)
	private LocalDateTime datumVremeOdrzavanja; 
	private double cena;
	private String kupacUsername;
	private StatusKarte status;
	private TipKarte tip;
	private int brojKarata;
	private boolean obrisana;

	public Karta() {
		
	}


	public Karta(String id, String manifestacijaId, String nazivManifestacije, LocalDateTime datumVremeOdrzavanja,
			double cena, String kupacUsername, StatusKarte status, TipKarte tip, int brojKarata, boolean obrisana) {
		super();
		this.id = id;
		this.manifestacijaId = manifestacijaId;
		this.nazivManifestacije = nazivManifestacije;
		this.datumVremeOdrzavanja = datumVremeOdrzavanja;
		this.cena = cena;
		this.kupacUsername = kupacUsername;
		this.status = status;
		this.tip = tip;
		this.brojKarata = brojKarata;
		this.obrisana = obrisana;
	}


	public String getManifestacijaId() {
		return manifestacijaId;
	}



	public void setManifestacijaId(String manifestacijaId) {
		this.manifestacijaId = manifestacijaId;
	}



	public String getKupacUsername() {
		return kupacUsername;
	}



	public void setKupacUsername(String kupacUsername) {
		this.kupacUsername = kupacUsername;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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


	public String getNazivManifestacije() {
		return nazivManifestacije;
	}


	public void setNazivManifestacije(String nazivManifestacije) {
		this.nazivManifestacije = nazivManifestacije;
	}





	public int getBrojKarata() {
		return brojKarata;
	}





	public void setBrojKarata(int brojKarata) {
		this.brojKarata = brojKarata;
	}


	public boolean isObrisana() {
		return obrisana;
	}


	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}
	
	

}
