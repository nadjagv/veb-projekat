package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import domain.Karta;
import domain.Komentar;
import domain.Lokacija;
import domain.Manifestacija;
import domain.Prodavac;
import helperClasses.CrudManifestacijaDTO;
import repositories.KartaRepository;
import repositories.KomentarRepository;
import repositories.ManifestacijaRepository;
import repositories.ProdavacRepository;
import utils.StringGenerator;

public class ManifestacijaService {
	ManifestacijaRepository manifestacijaRep;
	ProdavacRepository prodavacRep;
	KartaRepository kartaRep;
	KomentarRepository komentarRep;

	public ManifestacijaService() {
		manifestacijaRep = ManifestacijaRepository.getInstance();
		prodavacRep = ProdavacRepository.getInstance();
		kartaRep = KartaRepository.getInstance();
		komentarRep = KomentarRepository.getInstance();
	}
	
	public ArrayList<Manifestacija> preuzmiSve() {
		return manifestacijaRep.getManifestacije();
	}
	
public ArrayList<Manifestacija> preuzmiSveNeobrisane() {
		ArrayList<Manifestacija> sve = manifestacijaRep.getManifestacije();
		ArrayList<Manifestacija> rezultat = new ArrayList<Manifestacija>();
		for (Manifestacija manifestacija : sve) {
			if (!manifestacija.isObrisana()) {
				rezultat.add(manifestacija);
			}
		}
		return rezultat;
	}
	
	public Manifestacija preuzmiPoId(String id) {
		Manifestacija m = manifestacijaRep.getOneById(id);
		if (m.isObrisana()) {
			return null;
		}
		return m;
	}
	
	public Manifestacija napraviManifestaciju(CrudManifestacijaDTO dto) {
		if (dto.getNaziv() == null || dto.getDatumVremeOdrzavanja() == null || dto.getBrojMesta() == 0 || dto.getCenaRegular() == 0 || dto.getTip() == null) {
			return null;
		}else if (dto.getBrojMesta() <= 0 || dto.getCenaRegular() <= 0){
			return null;
		}else if (dto.getDatumVremeOdrzavanja().isBefore(LocalDateTime.now())){
			return null;
		}
		
		if (preklapaSeDatumMesto(dto)) {
			return null;
		}
		
		
		Manifestacija nova = new Manifestacija();
		nova.setNaziv(dto.getNaziv());
		nova.setDatumVremeOdrzavanja(dto.getDatumVremeOdrzavanja());
		nova.setBrojMesta(dto.getBrojMesta());
		nova.setCenaRegular(dto.getCenaRegular());
		nova.setTip(dto.getTip());
		
		Lokacija l = new Lokacija();
		l.setDrzava(dto.getDrzava());
		l.setGrad(dto.getGrad());
		l.setUlica(dto.getUlica());
		l.setPostanskiBroj(dto.getPostanskiBroj());
		l.setKucniBroj(dto.getKucniBroj());
		l.setGeoDuzina(dto.getGeoDuzina());
		l.setGeoSirina(dto.getGeoSirina());
		
		nova.setLokacija(l);
		
		
		String id;
		while (true) {
			id = StringGenerator.generateRandomString(10);
			if (manifestacijaRep.getOneById(id) == null)
				break;
		}
		nova.setId(id);
		nova.setAktivna(false);
		nova.setObrisana(false);
		nova.setSlobodnaMesta(dto.getBrojMesta());
		nova.setProdavacUsername(dto.getProdavacUsername());
		
		manifestacijaRep.add(nova);
		
		Prodavac p = prodavacRep.getOneByUsername(dto.getProdavacUsername());
		if (p == null || p.isObrisan()) {
			return null;
		}
		p.getManifestacijeIds().add(nova.getId());
		
		return nova;
	}
	
	public Manifestacija izmeniManifestaciju(CrudManifestacijaDTO dto) {
		Manifestacija m = manifestacijaRep.getOneById(dto.getId());
		if (m == null || m.isObrisana()) {
			return null;
		}
		
		if (dto.getNaziv() == null || dto.getDatumVremeOdrzavanja() == null || dto.getBrojMesta() == 0 || dto.getCenaRegular() == 0 || dto.getTip() == null) {
			return null;
		}else if (dto.getBrojMesta() <= 0 || dto.getCenaRegular() <= 0){
			return null;
		}else if (dto.getDatumVremeOdrzavanja().isBefore(LocalDateTime.now())){
			return null;
		}
		
		if (preklapaSeDatumMesto(dto)) {
			return null;
		}
		
		int razlikaUBrojuMesta = dto.getBrojMesta() - m.getBrojMesta();
		int noviBrojSlobodnih = m.getSlobodnaMesta() + razlikaUBrojuMesta;
		if (noviBrojSlobodnih < 0) {
			return null;
		}
		
		m.setSlobodnaMesta(noviBrojSlobodnih);
		
		m.setNaziv(dto.getNaziv());
		m.setDatumVremeOdrzavanja(dto.getDatumVremeOdrzavanja());
		m.setBrojMesta(dto.getBrojMesta());
		m.setCenaRegular(dto.getCenaRegular());
		m.setTip(dto.getTip());
		
		
		Lokacija l = new Lokacija();
		l.setDrzava(dto.getDrzava());
		l.setGrad(dto.getGrad());
		l.setUlica(dto.getUlica());
		l.setPostanskiBroj(dto.getPostanskiBroj());
		l.setKucniBroj(dto.getKucniBroj());
		l.setGeoDuzina(dto.getGeoDuzina());
		l.setGeoSirina(dto.getGeoSirina());
		
		m.setLokacija(l);
		
		manifestacijaRep.save();
		
		return m;
	}
	
	public boolean obrisiManifestaciju(String id) {
		
		Manifestacija m = manifestacijaRep.getOneById(id);
		if (m == null || m.isObrisana()) {
			return false;
		}
		m.setObrisana(true);
		
		ArrayList<Karta> karte = kartaRep.getKarte();
		for (Karta karta : karte) {
			if (karta.getManifestacijaId().equals(id)) {
				karta.setObrisana(true);
			}
		}
		kartaRep.save();
		
		ArrayList<Komentar> komentari = komentarRep.getKomentari();
		for (Komentar komentar : komentari) {
			if (komentar.getManifestacijaId().equals(id)) {
				komentar.setObrisan(true);
			}
		}
		komentarRep.save();
		
		manifestacijaRep.save();
		return true;
	}

	
	public boolean preklapaSeDatumMesto(CrudManifestacijaDTO dto) {
		ArrayList<Manifestacija> manifestacije = preuzmiSveNeobrisane();
		Lokacija l = new Lokacija();
		l.setDrzava(dto.getDrzava());
		l.setGrad(dto.getGrad());
		l.setUlica(dto.getUlica());
		l.setPostanskiBroj(dto.getPostanskiBroj());
		l.setKucniBroj(dto.getKucniBroj());
		
		LocalDateTime pocetak = dto.getDatumVremeOdrzavanja();
		LocalDateTime kraj = pocetak.plusHours(3);
		
		for (Manifestacija m : manifestacije) {
			
			if (l.equals(m.getLokacija())) {
				LocalDateTime pocetakMan = m.getDatumVremeOdrzavanja();
				LocalDateTime krajMan = pocetakMan.plusHours(3);
				if (pocetak.isBefore(krajMan) && pocetakMan.isBefore(kraj) && !m.getId().equalsIgnoreCase(dto.getId())) {
					return true;
				}
			}
			
		} 
		return false;
	}
	
	public boolean aktivirajManifestaciju(String id) {
		Manifestacija m = manifestacijaRep.getOneById(id);
		if (m == null || m.isObrisana()) {
			return false;
		}
		m.setAktivna(true);
		manifestacijaRep.save();
		return true;
		
	}
	
	public boolean promeniSliku(String id, String filename) {
		Manifestacija m = manifestacijaRep.getOneById(id);
		if (m == null || m.isObrisana()) {
			return false;
		}
		m.setSlikaPath(filename);
		manifestacijaRep.save();
		return true;
		
	}
	
	
	
	

}
