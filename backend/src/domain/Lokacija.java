package domain;

public class Lokacija {
	private double geoDuzina, geoSirina;
	private String ulica, grad, drzava, postanskiBroj;
	private String kucniBroj;

	public Lokacija() {
		// TODO Auto-generated constructor stub
	}

	

	public Lokacija(double geoDuzina, double geoSirina, String ulica, String grad, String drzava, String postanskiBroj,
			String kucniBroj) {
		super();
		this.geoDuzina = geoDuzina;
		this.geoSirina = geoSirina;
		this.ulica = ulica;
		this.grad = grad;
		this.drzava = drzava;
		this.postanskiBroj = postanskiBroj;
		this.kucniBroj = kucniBroj;
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

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}



	public String getDrzava() {
		return drzava;
	}



	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}



	public String getKucniBroj() {
		return kucniBroj;
	}



	public void setKucniBroj(String kucniBroj) {
		this.kucniBroj = kucniBroj;
	}



	public boolean equals(Lokacija l) {
		if (l.getDrzava().equalsIgnoreCase(drzava) && l.getGrad().equalsIgnoreCase(grad) && 
				l.getPostanskiBroj().equalsIgnoreCase(postanskiBroj) && l.getUlica().equalsIgnoreCase(ulica) && l.getKucniBroj().equalsIgnoreCase(kucniBroj)) {
			return true;
		}
		return false;
	}



	

	
	
	

}
