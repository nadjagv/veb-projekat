package controller;

import static spark.Spark.get;

import com.google.gson.Gson;

import domain.Kupac;
import service.KupacService;

public class KupacController {

	private static Gson gson = new Gson();

	public KupacController() {
		KupacService kupacService = new KupacService();
		
		get("/kupci", (req, res) -> {
			return gson.toJson(kupacService.preuzmiSve());
		});
		
		get("/kupci/:username", (req, res) -> {
			Kupac a = kupacService.preuzmiPoUsername(req.params("username"));
			return gson.toJson(a);
		});
	}

}
