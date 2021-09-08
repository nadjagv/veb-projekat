package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.time.LocalDateTime;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Karta;
import domain.Manifestacija;
import helperClasses.CrudManifestacijaDTO;
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
		
		post("/karte/rezervisi", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			Karta dto = jsonb.fromJson(payload, Karta.class);
			
			
			boolean uspesno = kartaService.rezervisiKartu(dto);
			if (uspesno) {
				res.status(200);
				return "Uspeh";
			}
			

			res.status(400);
			return "Neuspesna rezervacija.";
			
		});
		
		post("/karte/otkazi", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			Karta dto = jsonb.fromJson(payload, Karta.class);
			
			
			boolean uspesno = kartaService.otkaziKartu(dto);
			if (uspesno) {
				res.status(200);
				return "Uspeh";
			}
			

			res.status(400);
			return "Neuspesno otkazivanje.";
			
		});
	}

}
