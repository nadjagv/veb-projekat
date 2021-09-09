package controller;

import static spark.Spark.get;
import static spark.Spark.put;

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
		
		get("/kupci/mojeKarteSve/:username", (req, res) -> {
			String username = req.params("username");
			Kupac k = kupacService.preuzmiPoUsername(username);
			
			//dodati proveru tokena
			if( k == null) {
				res.status(400);
				return "Nepostojeci kupac.";
			}
			res.status(200);
			return jsonb.toJson(kupacService.preuzmiSveKarteKupca(username));
		});
		
		get("/kupci/mojeManifestacije/:username", (req, res) -> {
			String username = req.params("username");
			Kupac k = kupacService.preuzmiPoUsername(username);
			
			//dodati proveru tokena
			if( k == null) {
				res.status(400);
				return "Nepostojeci kupac.";
			}
			res.status(200);
			return jsonb.toJson(kupacService.preuzmiManifestacijeKupca(username));
		});
		
		get("/kupci/sumnjivi/pregled", (req, res) -> {
			return jsonb.toJson(kupacService.preuzmiSumnjiveKupce());
		});
		
		put("/kupci/sumnjivi/blokiraj/:username", (req, res) -> {
			
			String username = req.params("username");
			Kupac k = kupacService.preuzmiPoUsername(username);
			
			//dodati proveru tokena
			if( k == null) {
				res.status(400);
				return "Nepostojeci kupac.";
			}
			boolean uspeh = kupacService.blokirajKupca(username);
			if (uspeh) {
				res.status(200);
				return "Uspeh";
			}
			res.status(400);
			return "Greska.";
			
		});
		
	}

}
