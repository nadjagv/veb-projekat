package controller;

import static spark.Spark.post;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Korisnik;
import enums.Uloga;
import helperClasses.Kredencijali;
import service.KorisnikService;
import service.KupacService;
import service.ProdavacService;

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
			
			Korisnik pronadjen = korisnikService.pretraziPoUsername(req.params("username"));
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
	}

}
