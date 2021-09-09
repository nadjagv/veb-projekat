package controller;

import static spark.Spark.get;
import static spark.Spark.put;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Kupac;
import domain.Prodavac;
import service.ProdavacService;

public class ProdavacController {

	Jsonb jsonb = JsonbBuilder.newBuilder().build();

	public ProdavacController() {
		ProdavacService prodavacService = new ProdavacService();
		
		get("/prodavci", (req, res) -> {
			return jsonb.toJson(prodavacService.preuzmiSve());
		});
		
		get("/prodavci/:username", (req, res) -> {
			Prodavac a = prodavacService.preuzmiPoUsername(req.params("username"));
			return jsonb.toJson(a);
		});
		
		get("/prodavci/mojeMan/:username", (req, res) -> {
			String username = req.params("username");
			Prodavac a = prodavacService.preuzmiPoUsername(username);
			
			//dodati proveru tokena
			if( a == null) {
				res.status(400);
				return "Nepostojeci prodavac.";
			}
			res.status(200);
			return jsonb.toJson(prodavacService.preuzmiManifestacijeProdavca(username));
		});
		
		get("/prodavci/mojeKarte/:username", (req, res) -> {
			String username = req.params("username");
			Prodavac a = prodavacService.preuzmiPoUsername(username);
			
			//dodati proveru tokena
			if( a == null) {
				res.status(400);
				return "Nepostojeci prodavac.";
			}
			res.status(200);
			return jsonb.toJson(prodavacService.preuzmiRezKarteProdavca(username));
		});
		
		get("/prodavci/mojiKupci/:username", (req, res) -> {
			String username = req.params("username");
			Prodavac a = prodavacService.preuzmiPoUsername(username);
			
			//dodati proveru tokena
			if( a == null) {
				res.status(400);
				return "Nepostojeci prodavac.";
			}
			res.status(200);
			return jsonb.toJson(prodavacService.preuzmiKupceRezKarataProdavca(username));
		});
		
		put("/prodavci/blokiraj/:username", (req, res) -> {
			
			String username = req.params("username");
			Prodavac k = prodavacService.preuzmiPoUsername(username);
			
			//dodati proveru tokena
			if( k == null) {
				res.status(400);
				return "Nepostojeci prodavac.";
			}
			boolean uspeh = prodavacService.blokirajProdavca(username);
			if (uspeh) {
				res.status(200);
				return "Uspeh";
			}
			res.status(400);
			return "Greska.";
			
		});
	}

}
