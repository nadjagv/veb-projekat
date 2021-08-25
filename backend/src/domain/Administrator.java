package domain;

import java.time.LocalDate;

import enums.Pol;
import enums.Uloga;

public class Administrator extends Korisnik {

	public Administrator() {
		// TODO Auto-generated constructor stub
	}

	public Administrator(String username, String password, String ime, String prezime, Pol pol, LocalDate datumRodjenja,
			Uloga uloga) {
		super(username, password, ime, prezime, pol, datumRodjenja, uloga);
		// TODO Auto-generated constructor stub
	}

}
