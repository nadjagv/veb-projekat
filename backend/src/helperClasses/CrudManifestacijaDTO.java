package helperClasses;

import java.time.LocalDateTime;

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
	private LocalDateTime datum;
	private double cena;
	private int brojMesta;
	private String slikaPath;  
	private String prodavacUsername;
	

	public CrudManifestacijaDTO() {
		// TODO Auto-generated constructor stub
	}


	public CrudManifestacijaDTO(String naziv, String grad, String drzava, String postanskiBroj, String ulica,
			String kucniBroj, TipManifestacije tip, LocalDateTime datum, double cena, int brojMesta,
			String slikaPath, String prodavacUsername) {
		super();
		this.naziv = naziv;
		this.grad = grad;
		this.drzava = drzava;
		this.postanskiBroj = postanskiBroj;
		this.ulica = ulica;
		this.kucniBroj = kucniBroj;
		this.tip = tip;
		this.datum = datum;
		this.cena = cena;
		this.brojMesta = brojMesta;
		this.slikaPath = slikaPath;
	}


	public CrudManifestacijaDTO(String id, String naziv, String grad, String drzava, String postanskiBroj, String ulica,
			String kucniBroj, TipManifestacije tip, LocalDateTime datum, double cena, int brojMesta, String slikaPath, String prodavacUsername) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.grad = grad;
		this.drzava = drzava;
		this.postanskiBroj = postanskiBroj;
		this.ulica = ulica;
		this.kucniBroj = kucniBroj;
		this.tip = tip;
		this.datum = datum;
		this.cena = cena;
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


	public LocalDateTime getDatum() {
		return datum;
	}


	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}


	public double getCena() {
		return cena;
	}


	public void setCena(double cena) {
		this.cena = cena;
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
	
	

}
