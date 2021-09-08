package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import domain.Lokacija;
import domain.Manifestacija;
import domain.Prodavac;
import helperClasses.CrudManifestacijaDTO;
import repositories.ManifestacijaRepository;
import repositories.ProdavacRepository;
import utils.StringGenerator;

public class ManifestacijaService {
	ManifestacijaRepository manifestacijaRep;
	ProdavacRepository prodavacRep;

	public ManifestacijaService() {
		manifestacijaRep = ManifestacijaRepository.getInstance();
		prodavacRep = ProdavacRepository.getInstance();
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
		return manifestacijaRep.getOneById(id);
	}
	
	public Manifestacija napraviManifestaciju(CrudManifestacijaDTO dto) {
		if (dto.getNaziv() == null || dto.getDatum() == null || dto.getBrojMesta() == 0 || dto.getCena() == 0 || dto.getTip() == null || dto.getSlikaPath()==null) {
			return null;
		}else if (dto.getBrojMesta() <= 0 || dto.getCena() <= 0){
			return null;
		}else if (dto.getDatum().isBefore(LocalDateTime.now())){
			return null;
		}
		
		if (preklapaSeDatumMesto(dto)) {
			return null;
		}
		
		
		Manifestacija nova = new Manifestacija();
		nova.setNaziv(dto.getNaziv());
		nova.setDatumVremeOdrzavanja(dto.getDatum());
		nova.setBrojMesta(dto.getBrojMesta());
		nova.setCenaRegular(dto.getCena());
		nova.setTip(dto.getTip());
		
		nova.setSlikaPath(dto.getSlikaPath());
		
		Lokacija l = new Lokacija();
		l.setDrzava(dto.getDrzava());
		l.setGrad(dto.getGrad());
		l.setUlica(dto.getUlica());
		l.setPostanskiBroj(dto.getPostanskiBroj());
		l.setKucniBroj(dto.getKucniBroj());
		
		nova.setLokacija(l);
		
		nova.setId(StringGenerator.generateRandomString(10));
		nova.setAktivna(false);
		nova.setObrisana(false);
		nova.setSlobodnaMesta(dto.getBrojMesta());
		nova.setProdavacUsername(dto.getProdavacUsername());
		
		manifestacijaRep.add(nova);
		
		Prodavac p = prodavacRep.getOneByUsername(dto.getProdavacUsername());
		
		p.getManifestacijeIds().add(nova.getId());
		
		return nova;
	}
	
	public Manifestacija izmeniManifestaciju(CrudManifestacijaDTO dto) {
		Manifestacija m = manifestacijaRep.getOneById(dto.getId());
		if (m == null) {
			return null;
		}
		
		if (dto.getNaziv() == null || dto.getDatum() == null || dto.getBrojMesta() == 0 || dto.getCena() == 0 || dto.getTip() == null || dto.getSlikaPath()==null) {
			return null;
		}else if (dto.getBrojMesta() <= 0 || dto.getCena() <= 0){
			return null;
		}else if (dto.getDatum().isBefore(LocalDateTime.now())){
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
		m.setDatumVremeOdrzavanja(dto.getDatum());
		m.setBrojMesta(dto.getBrojMesta());
		m.setCenaRegular(dto.getCena());
		m.setTip(dto.getTip());
		
		m.setSlikaPath(dto.getSlikaPath());
		
		Lokacija l = new Lokacija();
		l.setDrzava(dto.getDrzava());
		l.setGrad(dto.getGrad());
		l.setUlica(dto.getUlica());
		l.setPostanskiBroj(dto.getPostanskiBroj());
		l.setKucniBroj(dto.getKucniBroj());
		
		m.setLokacija(l);
		
		manifestacijaRep.save();
		
		return m;
	}
	
	public boolean obrisiManifestaciju(String id) {
		//proveriti uslove brisanja
		
		Manifestacija m = manifestacijaRep.getOneById(id);
		if (m == null) {
			return false;
		}
		m.setObrisana(true);
		
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
		
		LocalDateTime pocetak = dto.getDatum();
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
		if (!m.isObrisana()) {
			m.setAktivna(true);
			manifestacijaRep.save();
			return true;
		}
		return false;
		
	}

}
