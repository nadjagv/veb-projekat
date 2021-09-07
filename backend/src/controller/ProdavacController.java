package controller;

import static spark.Spark.get;

import com.google.gson.Gson;

import domain.Prodavac;
import service.ProdavacService;

public class ProdavacController {

	private static Gson gson = new Gson();

	public ProdavacController() {
		ProdavacService prodavacService = new ProdavacService();
		
		get("/prodavci", (req, res) -> {
			return gson.toJson(prodavacService.preuzmiSve());
		});
		
		get("/prodavci/:username", (req, res) -> {
			Prodavac a = prodavacService.preuzmiPoUsername(req.params("username"));
			return gson.toJson(a);
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
			return gson.toJson(prodavacService.preuzmiManifestacijeProdavca(username));
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
			return gson.toJson(prodavacService.preuzmiRezKarteProdavca(username));
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
			return gson.toJson(prodavacService.preuzmiKupceRezKarataProdavca(username));
		});
	}

}
