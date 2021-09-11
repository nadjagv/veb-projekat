package helperClasses;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;

import enums.TipManifestacije;

public class CrudManifestacijaDTO {
	
	private String id;
	private String naziv;
	private String grad;
	private String drzava;
	private String postanskiBroj;
	private String ulica;
	private String kucniBroj;
	private TipManifestacije tip;
	@JsonbDateFormat(JsonbDateFormat.TIME_IN_MILLIS)
	private LocalDateTime datumVremeOdrzavanja;
	private double cenaRegular;
	private int brojMesta;
	private String slikaPath;  
	private String prodavacUsername;
	private double geoDuzina;
	private double geoSirina;
	

	public CrudManifestacijaDTO() {
		// TODO Auto-generated constructor stub
	}


	public CrudManifestacijaDTO(String naziv, String grad, String drzava, String postanskiBroj, String ulica,
			String kucniBroj, TipManifestacije tip, LocalDateTime datumVremeOdrzavanja, double cenaRegular, int brojMesta,
			String slikaPath, String prodavacUsername, double geoDuzina, double geoSirina) {
		super();
		this.naziv = naziv;
		this.grad = grad;
		this.drzava = drzava;
		this.postanskiBroj = postanskiBroj;
		this.ulica = ulica;
		this.kucniBroj = kucniBroj;
		this.tip = tip;
		this.datumVremeOdrzavanja = datumVremeOdrzavanja;
		this.cenaRegular = cenaRegular;
		this.brojMesta = brojMesta;
		this.slikaPath = slikaPath;
		this.geoDuzina = geoDuzina;
		this.geoSirina = geoSirina;
	}


	public CrudManifestacijaDTO(String id, String naziv, String grad, String drzava, String postanskiBroj, String ulica,
			String kucniBroj, TipManifestacije tip, LocalDateTime datumVremeOdrzavanja, double cenaRegular, int brojMesta,
			String slikaPath, String prodavacUsername, double geoDuzina, double geoSirina) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.grad = grad;
		this.drzava = drzava;
		this.postanskiBroj = postanskiBroj;
		this.ulica = ulica;
		this.kucniBroj = kucniBroj;
		this.tip = tip;
		this.datumVremeOdrzavanja = datumVremeOdrzavanja;
		this.cenaRegular = cenaRegular;
		this.brojMesta = brojMesta;
		this.slikaPath = slikaPath;
		this.prodavacUsername = prodavacUsername;
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


	public String getGrad() {
		return grad;
	}


	public void setGrad(String grad) {
		this.grad = grad;
	}


	public String getDrzava() {
		return drzava;
	}


	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}


	public String getPostanskiBroj() {
		return postanskiBroj;
	}


	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}


	public String getUlica() {
		return ulica;
	}


	public void setUlica(String ulica) {
		this.ulica = ulica;
	}


	public String getKucniBroj() {
		return kucniBroj;
	}


	public void setKucniBroj(String kucniBroj) {
		this.kucniBroj = kucniBroj;
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


	public String getSlikaPath() {
		return slikaPath;
	}


	public void setSlikaPath(String slikaPath) {
		this.slikaPath = slikaPath;
	}


	public String getProdavacUsername() {
		return prodavacUsername;
	}


	public void setProdavacUsername(String prodavacUsername) {
		this.prodavacUsername = prodavacUsername;
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


	public double getGeoDuzina() {
		return geoDuzina;
	}


	public void setGeoDuzina(double geoDuzina) {
		this.geoDuzina = geoDuzina;
	}


	public double getGeoSirina() {
		return geoSirina;
	}


	public void setGeoSirina(double geoSirina) {
		this.geoSirina = geoSirina;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	

}
