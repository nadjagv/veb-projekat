package domain;

import enums.ImeTipaKupca;

public class TipKupca {
	
	private ImeTipaKupca ime;
	private double popust;
	private double potrebniBodovi;

	public TipKupca() {
		// TODO Auto-generated constructor stub
	}

	public TipKupca(ImeTipaKupca ime, double popust, double potrebniBodovi) {
		super();
		this.ime = ime;
		this.popust = popust;
		this.potrebniBodovi = potrebniBodovi;
	}

	public ImeTipaKupca getIme() {
		return ime;
	}

	public void setIme(ImeTipaKupca ime) {
		this.ime = ime;
	}

	public double getPopust() {
		return popust;
	}

	public void setPopust(double popust) {
		this.popust = popust;
	}

	public double getPotrebniBodovi() {
		return potrebniBodovi;
	}

	public void setPotrebniBodovi(double potrebniBodovi) {
		this.potrebniBodovi = potrebniBodovi;
	}
	
	

}
