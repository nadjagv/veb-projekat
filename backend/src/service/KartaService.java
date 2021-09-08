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
		return kartaRep.getKarte();
	}
	
	public Karta preuzmiPoId(String id) {
		return kartaRep.getOneById(id);
	}
	
	public ArrayList<Karta> preuzmiSveRezervisane() {
		ArrayList<Karta> sve = kartaRep.getKarte();
		ArrayList<Karta> rezultat = new ArrayList<Karta>();
		
		for (Karta karta : sve) {
			if (karta.getStatus().equals(StatusKarte.REZERVISANA)) {
				rezultat.add(karta);
			}
		}
		
		return rezultat;
	}
	
	public boolean rezervisiKartu(Karta karta) {
		Karta nova = new Karta();
		
		Kupac k = kupacRep.getOneByUsername(karta.getKupacUsername());
		if (k == null) {
			return false;
		}
		
		Manifestacija m = manifestacijaRep.getOneById(karta.getManifestacijaId());
		if (m == null || !m.isAktivna() || m.getSlobodnaMesta() < karta.getBrojKarata()) {
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
		}
		
		double ukupnaCena = karta.getBrojKarata()* m.getCenaRegular() * multiplier * popust;
		nova.setCena(ukupnaCena);
		
		double bodovi = ukupnaCena / (1000*133*4);
		
		
		k.setBrojBodova(k.getBrojBodova() + bodovi);
		k.getKarteIds().add(nova.getId());
		kupacRep.save();
		
		m.setSlobodnaMesta(m.getSlobodnaMesta() - karta.getBrojKarata());
		manifestacijaRep.save();
		
		kartaRep.add(nova);
		
		return true;
		
	}
	
	public boolean otkaziKartu(Karta karta) {
		
		if (karta.getDatumVremeOdrzavanja().minusDays(7).isBefore(LocalDateTime.now())) {
			return false;
		}
		
		Kupac kupac = kupacRep.getOneByUsername(karta.getKupacUsername());
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
		
		Manifestacija m = manifestacijaRep.getOneById(karta.getManifestacijaId());
		m.setSlobodnaMesta(m.getSlobodnaMesta() + karta.getBrojKarata());
		
		Otkazivanje o = new Otkazivanje(LocalDateTime.now(), kupac.getUsername());
		
		kupac.getOtkazivanja().add(o);
		
		kupacRep.save();
		kartaRep.save();
		manifestacijaRep.save();
		
		return true;
	}
	

}
