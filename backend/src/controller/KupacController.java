package controller;

import static spark.Spark.get;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Kupac;
import domain.Prodavac;
import service.KupacService;

public class KupacController {

	Jsonb jsonb = JsonbBuilder.newBuilder().build();

	public KupacController() {
		KupacService kupacService = new KupacService();
		
		get("/kupci", (req, res) -> {
			return jsonb.toJson(kupacService.preuzmiSve());
		});
		
		get("/kupci/:username", (req, res) -> {
			Kupac a = kupacService.preuzmiPoUsername(req.params("username"));
			return jsonb.toJson(a);
		});
		
		get("/kupci/mojeKarte/:username", (req, res) -> {
			String username = req.params("username");
			Kupac k = kupacService.preuzmiPoUsername(username);
			
			//dodati proveru tokena
			if( k == null) {
				res.status(400);
				return "Nepostojeci kupac.";
			}
			res.status(200);
			return jsonb.toJson(kupacService.preuzmiRezervisaneKarteKupca(username));
		});
	}

}
