package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import domain.Karta;
import domain.Komentar;
import domain.Korisnik;
import domain.Kupac;
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

	public KomentarService() {
		komentarRep = KomentarRepository.getInstance();
		kupacRep = KupacRepository.getInstance();
		kartaRep = KartaRepository.getInstance();
	}
	
	public ArrayList<Komentar> preuzmiSve() {
		return komentarRep.getKomentari();
	}
	
	public Komentar preuzmiPoId(String id) {
		return komentarRep.getOneById(id);
	}
	
	public ArrayList<Komentar> preuzmiSveZaManifestaciju(String id){
		ArrayList<Komentar> svi = komentarRep.getKomentari();
		ArrayList<Komentar> rezultat = new ArrayList<Komentar>();
		for (Komentar k : svi) {
			if (k.getManifestacijaId().equalsIgnoreCase(id)) {
				rezultat.add(k);
			}
		}
		return rezultat;
	}
	
	public ArrayList<Komentar> preuzmiPrihvaceneZaManifestaciju(String id){
		ArrayList<Komentar> svi = komentarRep.getKomentari();
		ArrayList<Komentar> rezultat = new ArrayList<Komentar>();
		for (Komentar k : svi) {
			if (k.getManifestacijaId().equalsIgnoreCase(id) && k.getStatus().equals(StatusKomentara.PRIHVACEN)) {
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
		if (kupac == null)
			return false;
		
		ArrayList<String> karteIds = kupac.getKarteIds();
		for (String string : karteIds) {
			Karta karta = kartaRep.getOneById(string);
			if (karta != null) {
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
		if (kom == null)
			return false;
		
		kom.setStatus(StatusKomentara.PRIHVACEN);
		komentarRep.save();
		
		return true;
	}
	
	public boolean odbijKomentar(String id) {
		Komentar kom = komentarRep.getOneById(id);
		if (kom == null)
			return false;
		
		kom.setStatus(StatusKomentara.ODBIJEN);
		komentarRep.save();
		
		return true;
	}

}
