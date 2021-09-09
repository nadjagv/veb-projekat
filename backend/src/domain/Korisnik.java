package domain;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;

import enums.Pol;
import enums.Uloga;

public class Korisnik {
	
	private String username, password, ime, prezime;
	private Pol pol;
	@JsonbDateFormat(JsonbDateFormat.TIME_IN_MILLIS)
	private LocalDate datumRodjenja;
	private Uloga uloga;
	private boolean obrisan;
	private String JWTToken;
	
	
	
	public Korisnik() {
		super();
	}



	public Korisnik(String username, String password, String ime, String prezime, Pol pol, LocalDate datumRodjenja,
			Uloga uloga, boolean obrisan) {
		super();
		this.username = username;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datumRodjenja = datumRodjenja;
		this.uloga = uloga;
		this.obrisan = obrisan;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getIme() {
		return ime;
	}



	public void setIme(String ime) {
		this.ime = ime;
	}



	public String getPrezime() {
		return prezime;
	}



	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}



	public Pol getPol() {
		return pol;
	}



	public void setPol(Pol pol) {
		this.pol = pol;
	}



	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}



	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}



	public Uloga getUloga() {
		return uloga;
	}



	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}



	public boolean isObrisan() {
		return obrisan;
	}



	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}



	public String getJWTToken() {
		return JWTToken;
	}



	public void setJWTToken(String jWTToken) {
		JWTToken = jWTToken;
	}
	
	
	
	
	

}
