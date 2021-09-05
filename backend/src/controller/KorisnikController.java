package controller;

import static spark.Spark.post;

import com.google.gson.Gson;

import domain.Korisnik;
import helperClasses.Kredencijali;
import service.KorisnikService;

public class KorisnikController {
	
	private static Gson gson = new Gson();

	public KorisnikController() {
		KorisnikService korisnikService = new KorisnikService();
		
		post("/korisnici/login", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			Kredencijali kred = gson.fromJson(payload, Kredencijali.class);
			
			Korisnik k = korisnikService.logIn(kred);
			if (k != null) {
				res.status(200);
				return gson.toJson(k);
			}
			
			res.status(400);
			return "Wrong credentials.";
		});
	}

}
