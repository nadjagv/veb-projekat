package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.time.LocalDateTime;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Karta;
import domain.Manifestacija;
import enums.Uloga;
import helperClasses.CrudManifestacijaDTO;
import service.KartaService;
import utils.TokenUtils;

public class KartaController {

	Jsonb jsonb = JsonbBuilder.newBuilder().build();

	public KartaController() {
		KartaService kartaService = new KartaService();
		
		get("/karte", (req, res) -> {
			Uloga uloga = TokenUtils.proveriToken(req);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			return jsonb.toJson(kartaService.preuzmiSve());
		});
		
		get("/karte/:id", (req, res) -> {
			Uloga uloga = TokenUtils.proveriToken(req);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			Karta m = kartaService.preuzmiPoId(req.params("id"));
			return jsonb.toJson(m);
		});
		
		post("/karte/rezervisi", (req, res) -> {
			Uloga uloga = TokenUtils.proveriToken(req);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}else if (uloga != Uloga.KUPAC) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
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
			Uloga uloga = TokenUtils.proveriToken(req);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}else if (uloga != Uloga.KUPAC) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			
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
