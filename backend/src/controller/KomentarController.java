package controller;

import static spark.Spark.get;

import com.google.gson.Gson;

import domain.Komentar;
import service.KomentarService;

public class KomentarController {

	private static Gson gson = new Gson();

	public KomentarController() {
		KomentarService komentarService = new KomentarService();
		
		get("/komentari", (req, res) -> {
			return gson.toJson(komentarService.preuzmiSve());
		});
		
		get("/komentari/:id", (req, res) -> {
			Komentar m = komentarService.preuzmiPoId(req.params("id"));
			return gson.toJson(m);
		});
	}

}
