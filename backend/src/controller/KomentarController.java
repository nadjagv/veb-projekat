package controller;

import static spark.Spark.get;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Komentar;
import service.KomentarService;

public class KomentarController {

	Jsonb jsonb = JsonbBuilder.newBuilder().build();

	public KomentarController() {
		KomentarService komentarService = new KomentarService();
		
		get("/komentari", (req, res) -> {
			return jsonb.toJson(komentarService.preuzmiSve());
		});
		
		get("/komentari/:id", (req, res) -> {
			Komentar m = komentarService.preuzmiPoId(req.params("id"));
			return jsonb.toJson(m);
		});
	}

}
