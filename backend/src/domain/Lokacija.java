package domain;

public class Lokacija {
	private double geoDuzina, geoSirina;
	private String ulica, grad, postanskiBroj;
	private int kucniBroj;

	public Lokacija() {
		// TODO Auto-generated constructor stub
	}

	public Lokacija(double geoDuzina, double geoSirina, String ulica, String grad, String postanskiBroj,
			int kucniBroj) {
		super();
		this.geoDuzina = geoDuzina;
		this.geoSirina = geoSirina;
		this.ulica = ulica;
		this.grad = grad;
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

	public int getKucniBroj() {
		return kucniBroj;
	}

	public void setKucniBroj(int kucniBroj) {
		this.kucniBroj = kucniBroj;
	}
	
	

}
