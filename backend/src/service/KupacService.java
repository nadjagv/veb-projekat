package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.BoldAction;

import domain.Karta;
import domain.Korisnik;
import domain.Kupac;
import domain.Manifestacija;
import domain.Otkazivanje;
import domain.Prodavac;
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
		ArrayList<Kupac> svi = kupacRep.getKupci();
		ArrayList<Kupac> rezultat = new ArrayList<Kupac>();
		for (Kupac kupac : svi) {
			if (!kupac.isObrisan()) {
				rezultat.add(kupac);
			}
		}
		return rezultat;
	}
	
	public Kupac preuzmiPoUsername(String username) {
		Kupac p = kupacRep.getOneByUsername(username);
		if (p == null || p.isObrisan()) {
			return null;
		}
		return p;
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
	
	public ArrayList<Kupac> preuzmiSumnjiveKupce(){
		ArrayList<Kupac> svi = preuzmiSve();
		ArrayList<Kupac> sumnjivi = new ArrayList<Kupac>();
		for (Kupac kupac : svi) {
			ArrayList<Otkazivanje> otkazivanja = kupac.getOtkazivanja();
			if (otkazivanja == null || otkazivanja.size() <= 5) {
				continue;
			}
			
			int brojac = 0;
			for (Otkazivanje o : otkazivanja) {
				
				if (o.getDatum().isAfter(LocalDateTime.now().minusMonths(1))) {
					brojac++;
				}
			}
			
			if (brojac > 5) {
				sumnjivi.add(kupac);
			}
		}
		
		return sumnjivi;
		
	}
	
	public boolean blokirajKupca(String username) {
		Kupac kupac = kupacRep.getOneByUsername(username);
		if (kupac == null || kupac.isObrisan()) {
			return false;
		}
		
		ArrayList<Kupac> sumnjivi = preuzmiSumnjiveKupce();
		boolean pronadjen = false;
		for (Kupac s : sumnjivi) {
			if (s.getUsername().equals(username) && !s.isObrisan()) {
				pronadjen = true;
				break;
			}
		}
		if(pronadjen) {
			kupac.setBlokiran(true);
			return true;
		}
		return false;
	}

}
