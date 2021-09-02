package controller;

import static spark.Spark.get;

import com.google.gson.Gson;

import domain.Karta;
import service.KartaService;

public class KartaController {

	private static Gson gson = new Gson();

	public KartaController() {
		KartaService kartaService = new KartaService();
		
		get("/karte", (req, res) -> {
			return gson.toJson(kartaService.preuzmiSve());
		});
		
		get("/karte/:id", (req, res) -> {
			Karta m = kartaService.preuzmiPoId(req.params("id"));
			return gson.toJson(m);
		});
	}

}
