package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import domain.Karta;
import domain.Kupac;
import domain.Manifestacija;
import domain.Otkazivanje;
import domain.TipKupca;
import enums.ImeTipaKupca;
import enums.StatusKarte;
import enums.TipKarte;
import podesavanja.Podesavanja;
import repositories.KartaRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;
import utils.StringGenerator;

public class KartaService {

	KartaRepository kartaRep;
	KupacRepository kupacRep;
	ManifestacijaRepository manifestacijaRep;
	Podesavanja podesavanja;

	public KartaService() {
		kartaRep = KartaRepository.getInstance();
		kupacRep = KupacRepository.getInstance();
		manifestacijaRep = ManifestacijaRepository.getInstance();
		podesavanja = Podesavanja.getInstance();
	}
	
	public ArrayList<Karta> preuzmiSve() {
		ArrayList<Karta> sve = kartaRep.getKarte();
		ArrayList<Karta> rezultat = new ArrayList<Karta>();
		
		for (Karta karta : sve) {
			if (!karta.isObrisana()) {
				rezultat.add(karta);
			}
		}
		
		return rezultat;
	}
	
	public Karta preuzmiPoId(String id) {
		Karta k = kartaRep.getOneById(id);
		if (k.isObrisana()) {
			return null;
		}
		return k;
	}
	
	public ArrayList<Karta> preuzmiSveRezervisane() {
		ArrayList<Karta> sve = kartaRep.getKarte();
		ArrayList<Karta> rezultat = new ArrayList<Karta>();
		
		for (Karta karta : sve) {
			if (karta.getStatus().equals(StatusKarte.REZERVISANA) && !karta.isObrisana()) {
				rezultat.add(karta);
			}
		}
		
		return rezultat;
	}
	
	public boolean rezervisiKartu(Karta karta) {
		Karta nova = new Karta();
		
		Kupac k = kupacRep.getOneByUsername(karta.getKupacUsername());
		if (k == null || k.isObrisan()) {
			return false;
		}
		
		Manifestacija m = manifestacijaRep.getOneById(karta.getManifestacijaId());
		if (m == null || !m.isAktivna() || m.getSlobodnaMesta() < karta.getBrojKarata() || m.isObrisana()) {
			return false;
		}
		
		nova.setBrojKarata(karta.getBrojKarata());
		nova.setKupacUsername(karta.getKupacUsername());
		nova.setDatumVremeOdrzavanja(m.getDatumVremeOdrzavanja());
		
		String id;
		while (true) {
			id = StringGenerator.generateRandomString(10);
			if (kartaRep.getOneById(id) == null)
				break;
		}
		nova.setId(id);
		nova.setManifestacijaId(m.getId());
		nova.setNazivManifestacije(m.getNaziv());
		nova.setStatus(StatusKarte.REZERVISANA);
		nova.setTip(karta.getTip());
		
		int multiplier = 1;
		if (karta.getTip().equals(TipKarte.FAN_PIT)) {
			multiplier = 2;
		}else if (karta.getTip().equals(TipKarte.VIP)) {
			multiplier = 4;
		}
		
		double popust = 1;
		if (k.getTip().equals(ImeTipaKupca.SREBRNI)) {
			popust = podesavanja.getSrebrni().getPopust();
		}else if (k.getTip().equals(ImeTipaKupca.ZLATNI)) {
			popust = podesavanja.getZlatni().getPopust();
		}else if (k.getTip().equals(ImeTipaKupca.BRONZANI)) {
			popust = podesavanja.getBronzani().getPopust();
		}
		
		double ukupnaCena = karta.getBrojKarata()* m.getCenaRegular() * multiplier * popust;
		nova.setCena(ukupnaCena);
		
		double bodovi = ukupnaCena / (1000*133*4);
		
		
		k.setBrojBodova(k.getBrojBodova() + bodovi);
		
		if (k.getBrojBodova() >= podesavanja.getZlatni().getPotrebniBodovi()) {
			k.setTip(ImeTipaKupca.ZLATNI);
		}else if (k.getBrojBodova() >= podesavanja.getSrebrni().getPotrebniBodovi()) {
			k.setTip(ImeTipaKupca.SREBRNI);
		}else if (k.getBrojBodova() >= podesavanja.getBronzani().getPotrebniBodovi()) {
			k.setTip(ImeTipaKupca.BRONZANI);
		}
		
		k.getKarteIds().add(nova.getId());
		kupacRep.save();
		
		m.setSlobodnaMesta(m.getSlobodnaMesta() - karta.getBrojKarata());
		manifestacijaRep.save();
		
		kartaRep.add(nova);
		
		return true;
		
	}
	
	public boolean otkaziKartu(Karta k) {
		Karta karta = kartaRep.getOneById(k.getId());
		if (karta == null || karta.isObrisana()) {
			return false;
		}
		
		if (karta.getDatumVremeOdrzavanja().minusDays(7).isBefore(LocalDateTime.now())) {
			return false;
		}
		
		Kupac kupac = kupacRep.getOneByUsername(karta.getKupacUsername());
		if (kupac == null || kupac.isObrisan()) {
			return false;
		}
		double izgubljeniBodovi = karta.getBrojKarata() * karta.getCena() / (1000*133*4);
		
		karta.setStatus(StatusKarte.ODUSTANAK);
		
		kupac.setBrojBodova(kupac.getBrojBodova() - izgubljeniBodovi);
		
		
		if (kupac.getTip().equals(ImeTipaKupca.ZLATNI)) {
			if (kupac.getBrojBodova() < podesavanja.getZlatni().getPotrebniBodovi()) {
				kupac.setTip(ImeTipaKupca.SREBRNI);
			}
		}
		
		if (kupac.getTip().equals(ImeTipaKupca.SREBRNI)) {
			if (kupac.getBrojBodova() < podesavanja.getSrebrni().getPotrebniBodovi()) {
				kupac.setTip(ImeTipaKupca.BRONZANI);
			}
		}
		
		if (kupac.getTip().equals(ImeTipaKupca.BRONZANI)) {
			if (kupac.getBrojBodova() < podesavanja.getBronzani().getPotrebniBodovi()) {
				kupac.setTip(ImeTipaKupca.NOVI);
			}
		}
		
		Manifestacija m = manifestacijaRep.getOneById(karta.getManifestacijaId());
		if (m == null || m.isObrisana()) {
			return false;
		}
		m.setSlobodnaMesta(m.getSlobodnaMesta() + karta.getBrojKarata());
		if (m.getSlobodnaMesta() > m.getBrojMesta()) {
			m.setSlobodnaMesta(m.getBrojMesta());
		}
		Otkazivanje o = new Otkazivanje(LocalDateTime.now(), kupac.getUsername());
		
		if (kupac.getOtkazivanja() == null) {
			kupac.setOtkazivanja(new ArrayList<Otkazivanje>());
		}
		kupac.getOtkazivanja().add(o);
		
		kupacRep.save();
		kartaRep.save();
		manifestacijaRep.save();
		
		return true;
	}
	

}
