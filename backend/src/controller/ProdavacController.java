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
	}

}
