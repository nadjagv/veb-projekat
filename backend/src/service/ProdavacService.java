package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import domain.Karta;
import domain.Korisnik;
import domain.Kupac;
import domain.Manifestacija;
import domain.Prodavac;
import enums.StatusKarte;
import repositories.KartaRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;
import repositories.ProdavacRepository;

public class ProdavacService {

	ProdavacRepository prodavacRep;
	ManifestacijaRepository manifestacijaRep;
	KartaRepository kartaRep;
	KupacRepository kupacRep;

	public ProdavacService() {
		prodavacRep = ProdavacRepository.getInstance();
		manifestacijaRep = ManifestacijaRepository.getInstance();
		kartaRep = KartaRepository.getInstance();
		kupacRep = KupacRepository.getInstance();
	}
	
	public ArrayList<Prodavac> preuzmiSve() {
		ArrayList<Prodavac> svi = prodavacRep.getProdavci();
		ArrayList<Prodavac> rezultat = new ArrayList<Prodavac>();
		for (Prodavac prodavac : svi) {
			if (!prodavac.isObrisan()) {
				rezultat.add(prodavac);
			}
		}
		return rezultat;
	}
	
	public Prodavac preuzmiPoUsername(String username) {
		Prodavac p = prodavacRep.getOneByUsername(username);
		if (p == null || p.isObrisan()) {
			return null;
		}
		return p;
	}
	
	public Prodavac registruj(Korisnik k) {
		Prodavac p = new Prodavac(k.getUsername(), k.getPassword(), k.getIme(), k.getPrezime(), k.getPol(),k.getDatumRodjenja(), k.getUloga(), false, false);
		prodavacRep.add(p);
		return p;
	}
	
	public ArrayList<Manifestacija> preuzmiManifestacijeProdavca(String username){
		Prodavac p = prodavacRep.getOneByUsername(username);
		ArrayList<String> manId = p.getManifestacijeIds();

		ArrayList<Manifestacija> rezultat = new ArrayList<Manifestacija>();
		for (String string : manId) {
			Manifestacija m = manifestacijaRep.getOneById(string);
			if (m != null && !m.isObrisana()) {
				rezultat.add(m);
			}
			
		}
		return rezultat;
	}
	
	public ArrayList<Karta> preuzmiRezKarteManifestacije(String manId){
		ArrayList<Karta> sve = kartaRep.getKarte();
		ArrayList<Karta> rezultat = new ArrayList<Karta>();
		for (Karta k : sve) {
			if(k.getManifestacijaId().contentEquals(manId) && k.getStatus().equals(StatusKarte.REZERVISANA)) {
				rezultat.add(k);
			}
		}
		
		return rezultat;
	}
	
	public ArrayList<Karta> preuzmiRezKarteProdavca(String username){
		ArrayList<Manifestacija> man = preuzmiManifestacijeProdavca(username);
		ArrayList<Karta> rezultat = new ArrayList<Karta>();
		for (Manifestacija m : man) {
			rezultat.addAll(preuzmiRezKarteManifestacije(m.getId()));
		}
		
		return rezultat;
	}
	
	public ArrayList<Kupac> preuzmiKupceRezKarataProdavca(String username){
		ArrayList<Karta> karte = preuzmiRezKarteProdavca(username);
		ArrayList<Kupac> rezultat = new ArrayList<Kupac>();
		ArrayList<String> usernames = new ArrayList<String>();
		for (Karta k : karte) {
			Kupac kupac = kupacRep.getOneByUsername(k.getKupacUsername());
			
			if(!usernames.contains(kupac.getUsername())) {
				rezultat.add(kupac);
				usernames.add(kupac.getUsername());
			}
			
		}
		return rezultat;
	}
	
	public boolean blokirajProdavca(String username) {
		Prodavac p = prodavacRep.getOneByUsername(username);
		if (p == null || p.isObrisan()) {
			return false;
		}
		
		ArrayList<String> manIds = p.getManifestacijeIds();
		for (String string : manIds) {
			Manifestacija m = manifestacijaRep.getOneById(string);
			if (m != null && !m.isObrisana() && m.getDatumVremeOdrzavanja().isAfter(LocalDateTime.now())) {
				return false;
			}
		}
		
		p.setBlokiran(true);
		prodavacRep.save();
		return true;
	}

}
