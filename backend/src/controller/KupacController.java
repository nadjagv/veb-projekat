package controller;

import static spark.Spark.get;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Kupac;
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
	}

}
