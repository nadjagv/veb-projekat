package controller;

import static spark.Spark.get;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Karta;
import service.KartaService;

public class KartaController {

	Jsonb jsonb = JsonbBuilder.newBuilder().build();

	public KartaController() {
		KartaService kartaService = new KartaService();
		
		get("/karte", (req, res) -> {
			return jsonb.toJson(kartaService.preuzmiSve());
		});
		
		get("/karte/:id", (req, res) -> {
			Karta m = kartaService.preuzmiPoId(req.params("id"));
			return jsonb.toJson(m);
		});
	}

}
