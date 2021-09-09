package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import domain.Karta;
import domain.Komentar;
import domain.Korisnik;
import domain.Kupac;
import domain.Manifestacija;
import enums.StatusKarte;
import enums.StatusKomentara;
import repositories.KartaRepository;
import repositories.KomentarRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;
import utils.StringGenerator;

public class KomentarService {

	KomentarRepository komentarRep;
	KupacRepository kupacRep;
	KartaRepository kartaRep;
	ManifestacijaRepository manifestacijaRep;

	public KomentarService() {
		komentarRep = KomentarRepository.getInstance();
		kupacRep = KupacRepository.getInstance();
		kartaRep = KartaRepository.getInstance();
		manifestacijaRep = ManifestacijaRepository.getInstance();
	}
	
	public ArrayList<Komentar> preuzmiSve() {
		ArrayList<Komentar> svi = komentarRep.getKomentari();
		ArrayList<Komentar> rezultat = new ArrayList<Komentar>();
		for (Komentar k : svi) {
			if (!k.isObrisan()) {
				rezultat.add(k);
			}
		}
		return rezultat;
	}
	
	public Komentar preuzmiPoId(String id) {
		Komentar k = komentarRep.getOneById(id);
		if (k.isObrisan()) {
			return null;
		}
		return k;
	}
	
	public ArrayList<Komentar> preuzmiSveZaManifestaciju(String id){
		ArrayList<Komentar> svi = preuzmiSve();
		ArrayList<Komentar> rezultat = new ArrayList<Komentar>();
		for (Komentar k : svi) {
			if (k.getManifestacijaId().equalsIgnoreCase(id) && !k.isObrisan()) {
				rezultat.add(k);
			}
		}
		return rezultat;
	}
	
	public ArrayList<Komentar> preuzmiPrihvaceneZaManifestaciju(String id){
		ArrayList<Komentar> svi = preuzmiSve();
		ArrayList<Komentar> rezultat = new ArrayList<Komentar>();
		for (Komentar k : svi) {
			if (k.getManifestacijaId().equalsIgnoreCase(id) && k.getStatus().equals(StatusKomentara.PRIHVACEN) && !k.isObrisan()) {
				rezultat.add(k);
			}
		}
		return rezultat;
	}
	
	public Komentar kreirajKomentar(Komentar kom) {
		Komentar novi = new Komentar();
		if (!dozvoljenoKomentarisanje(kom)) {
			return null;
		}
		
		Manifestacija m = manifestacijaRep.getOneById(kom.getManifestacijaId());
		if (m == null) {
			return null;
		}
		
		String id;
		while (true) {
			id = StringGenerator.generateRandomString(10);
			if (komentarRep.getOneById(id) == null)
				break;
		}
		
		novi.setId(id);
		novi.setKupacUsername(kom.getKupacUsername());
		novi.setManifestacijaId(kom.getManifestacijaId());
		novi.setOcena(kom.getOcena());
		novi.setStatus(StatusKomentara.KREIRAN);
		novi.setTekst(kom.getTekst());
		
		komentarRep.add(novi);
		
		return novi;
	}
	
	public boolean dozvoljenoKomentarisanje(Komentar kom) {
		Kupac kupac = kupacRep.getOneByUsername(kom.getKupacUsername());
		if (kupac == null || kupac.isObrisan())
			return false;
		
		ArrayList<String> karteIds = kupac.getKarteIds();
		for (String string : karteIds) {
			Karta karta = kartaRep.getOneById(string);
			if (karta != null && !karta.isObrisana()) {
				if (karta.getDatumVremeOdrzavanja().isBefore(LocalDateTime.now()) 
						&& karta.getStatus().equals(StatusKarte.REZERVISANA) 
						&& karta.getManifestacijaId().equalsIgnoreCase(kom.getManifestacijaId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean prihvatiKomentar(String id) {
		Komentar kom = komentarRep.getOneById(id);
		if (kom == null || kom.isObrisan())
			return false;
		
		Manifestacija m = manifestacijaRep.getOneById(kom.getManifestacijaId());
		if (m == null) {
			return false;
		}
		
		if (m.getBrojOcena() == 0) {
			m.setOcena(kom.getOcena());
		}else {
			m.setOcena((m.getOcena() * m.getBrojOcena() + kom.getOcena()) / (m.getBrojOcena() + 1));
		}
		m.setBrojOcena(m.getBrojOcena() + 1);
		
		manifestacijaRep.save();
		
		kom.setStatus(StatusKomentara.PRIHVACEN);
		komentarRep.save();
		
		return true;
	}
	
	public boolean odbijKomentar(String id) {
		Komentar kom = komentarRep.getOneById(id);
		if (kom == null || kom.isObrisan())
			return false;
		
		kom.setStatus(StatusKomentara.ODBIJEN);
		komentarRep.save();
		
		return true;
	}
	
	public boolean obrisiKomentar(String komId, String username) {
		Kupac kupac = kupacRep.getOneByUsername(username);
		if (kupac == null || kupac.isObrisan()) {
			return false;
		}
		
		Komentar komentar = komentarRep.getOneById(komId);
		if (komentar == null || komentar.isObrisan() || !komentar.getStatus().equals(StatusKomentara.KREIRAN)) {
			return false;
		}
		
		komentar.setObrisan(true);
		
		return true;
		
	}

}
