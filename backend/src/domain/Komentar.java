package domain;

import enums.StatusKomentara;

public class Komentar {
	private String id;
	private String kupacUsername;
	private String manifestacijaId;
	private String tekst;
	private double ocena;
	private StatusKomentara status;

	public Komentar() {
		// TODO Auto-generated constructor stub
	}



	public Komentar(String id, String kupacUsername, String manifestacijaId, String tekst, double ocena,
			StatusKomentara status) {
		super();
		this.id = id;
		this.kupacUsername = kupacUsername;
		this.manifestacijaId = manifestacijaId;
		this.tekst = tekst;
		this.ocena = ocena;
		this.status = status;
	}



	public StatusKomentara getStatus() {
		return status;
	}



	public void setStatus(StatusKomentara status) {
		this.status = status;
	}



	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getKupacUsername() {
		return kupacUsername;
	}


	public void setKupacUsername(String kupacUsername) {
		this.kupacUsername = kupacUsername;
	}


	public String getManifestacijaId() {
		return manifestacijaId;
	}


	public void setManifestacijaId(String manifestacijaId) {
		this.manifestacijaId = manifestacijaId;
	}


	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	

}
