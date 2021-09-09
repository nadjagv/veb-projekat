package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import domain.Karta;
import domain.Korisnik;
import domain.Kupac;
import domain.Manifestacija;
import enums.StatusKarte;
import repositories.KartaRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;

public class KupacService {
	
	KupacRepository kupacRep;
	KartaRepository kartaRep;
	ManifestacijaRepository manifestacijaRep;

	public KupacService() {
		kupacRep = KupacRepository.getInstance();
		kartaRep = KartaRepository.getInstance();
		manifestacijaRep = ManifestacijaRepository.getInstance();
	}
	
	public ArrayList<Kupac> preuzmiSve() {
		return kupacRep.getKupci();
	}
	
	public Kupac preuzmiPoUsername(String username) {
		return kupacRep.getOneByUsername(username);
	}
	
	public Kupac registruj(Korisnik k) {
		Kupac p = new Kupac(k.getUsername(), k.getPassword(), k.getIme(), k.getPrezime(), k.getPol(),k.getDatumRodjenja(), k.getUloga(), false, false);
		kupacRep.add(p);
		return p;
	}
	
	public ArrayList<Karta> preuzmiRezervisaneKarteKupca(String username) {
		ArrayList<Karta> rezultat = new ArrayList<Karta>();
		Kupac k = kupacRep.getOneByUsername(username);
		if (k == null || k.isObrisan()) {
			return null;
		}
		
		ArrayList<String> karteIds = k.getKarteIds();
		for (String id : karteIds) {
			Karta karta = kartaRep.getOneById(id);
			
			if (karta.getStatus().equals(StatusKarte.REZERVISANA))
				rezultat.add(karta);
		}
		
		return rezultat;
	}
	
	public ArrayList<Karta> preuzmiSveKarteKupca(String username) {
		ArrayList<Karta> rezultat = new ArrayList<Karta>();
		Kupac k = kupacRep.getOneByUsername(username);
		if (k == null || k.isObrisan()) {
			return null;
		}
		
		ArrayList<String> karteIds = k.getKarteIds();
		for (String id : karteIds) {
			Karta karta = kartaRep.getOneById(id);
			
			rezultat.add(karta);
				
		}
		
		return rezultat;
	}
	
	public ArrayList<Manifestacija> preuzmiManifestacijeKupca(String username){
		ArrayList<Karta> karte = preuzmiRezervisaneKarteKupca(username);
		ArrayList<Manifestacija> rezultat = new ArrayList<Manifestacija>();
		for (Karta k : karte ) {
			rezultat.add(manifestacijaRep.getOneById(k.getManifestacijaId()));
		}
		return rezultat;
	}

}
