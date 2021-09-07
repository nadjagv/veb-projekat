package domain;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;

import enums.TipManifestacije;

public class Manifestacija {
	private String id;
	private String naziv;
	private TipManifestacije tip;
	private int brojMesta;
	private int slobodnaMesta;
	@JsonbDateFormat(JsonbDateFormat.TIME_IN_MILLIS)
	private LocalDateTime datumVremeOdrzavanja;
	private double cenaRegular;
	private boolean aktivna;
	private Lokacija lokacija;
	private String slikaPath;
	private boolean obrisana;
	private double ocena;
	

	public Manifestacija() {
		// TODO Auto-generated constructor stub
	}





	public Manifestacija(String id, String naziv, TipManifestacije tip, int brojMesta,
			LocalDateTime datumVremeOdrzavanja, double cenaRegular, boolean aktivna, Lokacija lokacija,
			String slikaPath, boolean obrisana, double ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
		this.brojMesta = brojMesta;
		this.slobodnaMesta = brojMesta;
		this.datumVremeOdrzavanja = datumVremeOdrzavanja;
		this.cenaRegular = cenaRegular;
		this.aktivna = aktivna;
		this.lokacija = lokacija;
		this.slikaPath = slikaPath;
		this.obrisana = obrisana;
		this.ocena = ocena;
	}
	
	public Manifestacija(String id, String naziv, TipManifestacije tip, int brojMesta, int slobodnaMesta,
			LocalDateTime datumVremeOdrzavanja, double cenaRegular, boolean aktivna, Lokacija lokacija,
			String slikaPath, boolean obrisana, double ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
		this.brojMesta = brojMesta;
		this.slobodnaMesta = slobodnaMesta;
		this.datumVremeOdrzavanja = datumVremeOdrzavanja;
		this.cenaRegular = cenaRegular;
		this.aktivna = aktivna;
		this.lokacija = lokacija;
		this.slikaPath = slikaPath;
		this.obrisana = obrisana;
		this.ocena = ocena;
	}



	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
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


	


	public boolean isAktivna() {
		return aktivna;
	}





	public void setAktivna(boolean aktivna) {
		this.aktivna = aktivna;
	}





	public Lokacija getLokacija() {
		return lokacija;
	}


	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}



	public String getSlikaPath() {
		return slikaPath;
	}


	public void setSlikaPath(String slikaPath) {
		this.slikaPath = slikaPath;
	}

	public boolean isObrisana() {
		return obrisana;
	}

	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}


	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}


	public int getSlobodnaMesta() {
		return slobodnaMesta;
	}


	public void setSlobodnaMesta(int slobodnaMesta) {
		this.slobodnaMesta = slobodnaMesta;
	}
	
	

}
