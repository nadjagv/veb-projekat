package controller;

import static spark.Spark.delete;
import static spark.Spark.post;
import static spark.Spark.put;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Korisnik;
import domain.Manifestacija;
import enums.Uloga;
import helperClasses.Kredencijali;
import service.KorisnikService;
import service.KupacService;
import service.ProdavacService;
import utils.TokenUtils;

public class KorisnikController {
	
	Jsonb jsonb = JsonbBuilder.newBuilder().build();

	public KorisnikController() {
		KorisnikService korisnikService = new KorisnikService();
		KupacService kupacService = new KupacService();
		ProdavacService prodavacService = new ProdavacService();
		
		post("/korisnici/login", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			Kredencijali kred = jsonb.fromJson(payload, Kredencijali.class);
			
			Korisnik k = korisnikService.logIn(kred);
			if (k != null) {
				res.status(200);
				return jsonb.toJson(k);
			}
			
			res.status(400);
			return "Pogresni kredencijali.";
		});
		
		post("/korisnici/logout/:username", (req, res) -> {
			Uloga uloga = TokenUtils.proveriToken(req);
			System.out.println(uloga);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}else if (uloga != Uloga.PRODAVAC && uloga != Uloga.ADMINISTRATOR && uloga != Uloga.KUPAC) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			
			boolean k = korisnikService.logout(req.params("username"));
			if (k) {
				res.status(200);
				return "Uspeh";
			}
			
			res.status(400);
			return "Greska.";
		});
		
		post("/korisnici/postojiUsername/:username", (req, res) -> {
			res.type("application/json");
			
			
			Korisnik pronadjen = korisnikService.pretraziPoUsername(req.params("username"));
			if (pronadjen != null) {
				res.status(400);
				return "Username zauzet.";
			}
			
			res.status(200);
			return "Ok";
		});
		
		
		post("/korisnici/registracija", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			Korisnik k = jsonb.fromJson(payload, Korisnik.class);
			
			Korisnik pronadjen = korisnikService.pretraziPoUsername(k.getUsername());
			if (pronadjen != null) {
				res.status(400);
				return "Username zauzet.";
			}
			
			if (k.getUloga().equals(Uloga.KUPAC)) {
				kupacService.registruj(k);
			}else if(k.getUloga().equals(Uloga.PRODAVAC)) {
				prodavacService.registruj(k);
			}else {
				res.status(400);
				return "Bad request.";
			}
			
			res.status(200);
			return "Uspeh";
		});
		
		
		post("/korisnici/izmena", (req, res) -> {
			Uloga uloga = TokenUtils.proveriToken(req);
			System.out.println(uloga);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}else if (uloga != Uloga.PRODAVAC && uloga != Uloga.ADMINISTRATOR && uloga != Uloga.KUPAC) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			res.type("application/json");
			String payload = req.body();
			Korisnik kor = jsonb.fromJson(payload, Korisnik.class);
			
			if(kor.getIme() == null || kor.getPrezime() == null || kor.getDatumRodjenja() == null || kor.getPassword() == null
					||kor.getPol() == null || kor.getUloga() == null) {
				res.status(400);
				return "Nedostaju podaci.";
			}
			
			Korisnik k = korisnikService.izmeniPodatke(kor);
			if (k != null) {
				res.status(200);
				return jsonb.toJson(k);
			}
			
			res.status(400);
			return "Pogresni podaci.";
		});
		
		delete("/korisnici/:username", (req, res) -> {
			
			//provera tokena
			
			Uloga uloga = TokenUtils.proveriToken(req);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}else if (uloga != Uloga.ADMINISTRATOR) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			
			String username = req.params("username");
			
			Korisnik m = korisnikService.pretraziPoUsername(username);
			if (m == null) {
				res.status(400);
				return null;
			}
			
			boolean obrisano = korisnikService.obrisi(username);
			if (obrisano == false) {
				res.status(400);
				return "Neuspesno brisanje.";
			}
			
			res.status(200);
			return "Uspeh";
		});
		
	}

}
