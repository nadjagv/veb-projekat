package service;

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
	}
	
	public ArrayList<Prodavac> preuzmiSve() {
		return prodavacRep.getProdavci();
	}
	
	public Prodavac preuzmiPoUsername(String username) {
		return prodavacRep.getOneByUsername(username);
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
		manId.stream().forEach(m -> {
			rezultat.add(manifestacijaRep.getOneById(m));
		});
		return rezultat;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Karta> preuzmiRezKarteManifestacije(String manId){
		return (ArrayList<Karta>) kartaRep.getKarte().stream()
				.filter(k -> k.getManifestacijaId().contentEquals(manId) && k.getStatus().equals(StatusKarte.REZERVISANA));
		
	}
	
	public ArrayList<Karta> preuzmiRezKarteProdavca(String username){
		ArrayList<Manifestacija> man = preuzmiManifestacijeProdavca(username);
		ArrayList<Karta> rezultat = new ArrayList<Karta>();
		man.stream().forEach(m -> {
			rezultat.addAll(preuzmiRezKarteManifestacije(m.getId()));
		});
		return rezultat;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Kupac> preuzmiKupceRezKarataProdavca(String username){
		ArrayList<Karta> karte = preuzmiRezKarteProdavca(username);
		ArrayList<Kupac> rezultat = new ArrayList<Kupac>();
		karte.stream().forEach(k -> {
			rezultat.add(kupacRep.getOneByUsername(k.getKupacUsername()));
		});
		return (ArrayList<Kupac>) rezultat.stream().distinct();
	}

}
