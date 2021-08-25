package domain;

public class Komentar {
	private Kupac kupac;
	private Manifestacija manifestacija;
	private String tekst;
	private double ocena;

	public Komentar() {
		// TODO Auto-generated constructor stub
	}

	public Komentar(Kupac kupac, Manifestacija manifestacija, String tekst, double ocena) {
		super();
		this.kupac = kupac;
		this.manifestacija = manifestacija;
		this.tekst = tekst;
		this.ocena = ocena;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public Manifestacija getManifestacija() {
		return manifestacija;
	}

	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
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
