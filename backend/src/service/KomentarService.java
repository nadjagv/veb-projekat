package service;

import java.util.ArrayList;

import domain.Komentar;
import enums.StatusKomentara;
import repositories.KomentarRepository;
import utils.StringGenerator;

public class KomentarService {

	KomentarRepository komentarRep;

	public KomentarService() {
		komentarRep = KomentarRepository.getInstance();
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
		
		novi.setId(StringGenerator.generateRandomString(10));
		novi.setKupacUsername(kom.getKupacUsername());
		novi.setManifestacijaId(kom.getManifestacijaId());
		novi.setOcena(kom.getOcena());
		novi.setStatus(StatusKomentara.KREIRAN);
		novi.setTekst(kom.getTekst());
		
		komentarRep.add(novi);
		
		return novi;
	}
	
	public boolean prihvatiKomentar(String id) {
		Komentar kom = komentarRep.getOneById(id);
		if (kom == null)
			return false;
		
		kom.setStatus(StatusKomentara.PRIHVACEN);
		
		return true;
	}
	
	public boolean odbijKomentar(String id) {
		Komentar kom = komentarRep.getOneById(id);
		if (kom == null)
			return false;
		
		kom.setStatus(StatusKomentara.ODBIJEN);
		
		return true;
	}

}
