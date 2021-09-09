package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import domain.Karta;
import domain.Korisnik;
import domain.Kupac;
import domain.Manifestacija;
import domain.Prodavac;
import enums.Uloga;
import helperClasses.Kredencijali;
import repositories.AdministratorRepository;
import repositories.KartaRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;
import repositories.ProdavacRepository;
import utils.TokenUtils;

public class KorisnikService {
	
	AdministratorRepository administratorRep;
	KupacRepository kupacRep;
	ProdavacRepository prodavacRep;
	ManifestacijaRepository manifestacijaRep;
	KartaRepository karteRep;

	public KorisnikService() {
		administratorRep = AdministratorRepository.getInstance();
		kupacRep = KupacRepository.getInstance();
		prodavacRep = ProdavacRepository.getInstance();
		manifestacijaRep = ManifestacijaRepository.getInstance();
		karteRep = KartaRepository.getInstance();
	}
	
	public Korisnik pretraziPoUsername(String username) {
		Korisnik pronadjen;
		pronadjen = kupacRep.getOneByUsername(username);
		if (pronadjen != null && !pronadjen.isObrisan()) {
			return pronadjen;
		}
		pronadjen = prodavacRep.getOneByUsername(username);
		if (pronadjen != null && !pronadjen.isObrisan()) {
			return pronadjen;
		}
		pronadjen = administratorRep.getOneByUsername(username);
		if (pronadjen != null && !pronadjen.isObrisan()) {
			return pronadjen;
		}
		
		
		return null;
	}
	
	public Korisnik logIn(Kredencijali kred) {
		Korisnik korisnik = pretraziPoUsername(kred.getUsername());
		if (korisnik == null || korisnik.isObrisan())
			return null;
		if (korisnik.getPassword().equals(kred.getPassword())) {
			TokenUtils.napraviToken(korisnik);
			return korisnik;
		}
		return null;
	}
	
	public Korisnik izmeniPodatke(Korisnik k) {
		Korisnik korisnik = pretraziPoUsername(k.getUsername());
		
		if (korisnik == null || korisnik.isObrisan())
			return null;
		
		korisnik.setIme(k.getIme());
		korisnik.setPrezime(k.getPrezime());
		korisnik.setPassword(k.getPassword());
		korisnik.setPol(k.getPol());
		korisnik.setDatumRodjenja(k.getDatumRodjenja());
		
		if (k.getUloga().equals(Uloga.ADMINISTRATOR)) {
			administratorRep.save();
		}else if (k.getUloga().equals(Uloga.KUPAC)) {
			kupacRep.save();
		}else {
			prodavacRep.save();
		}
		
		return korisnik;
	}
	
	public boolean obrisi(String username) {
		Korisnik korisnik = pretraziPoUsername(username);
		
		if (korisnik == null || korisnik.isObrisan())
			return false;
		
		if (korisnik.getUloga().equals(Uloga.PRODAVAC)) {
			Prodavac p = (Prodavac) korisnik;
			ArrayList<String> manIds = p.getManifestacijeIds();
			for (String string : manIds) {
				Manifestacija m = manifestacijaRep.getOneById(string);
				if (m != null && !m.isObrisana() && m.getDatumVremeOdrzavanja().isAfter(LocalDateTime.now())) {
					return false;
				}
			}
			
			p.setObrisan(true);
			prodavacRep.save();
			
		}else if (korisnik.getUloga().equals(Uloga.KUPAC)) {
			Kupac kupac = (Kupac) korisnik;
			ArrayList<String> karteIds = kupac.getKarteIds();
			for (String string : karteIds) {
				Karta m = karteRep.getOneById(string);
				if (m != null && !m.isObrisana() && m.getDatumVremeOdrzavanja().isAfter(LocalDateTime.now())) {
					return false;
				}
			}
			
			kupac.setObrisan(true);
			kupacRep.save();
		}
		
		return true;
	}
	
	

}
