package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;


import domain.Komentar;
import helperClasses.CrudManifestacijaDTO;
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
		
		//kreiranje
		post("/komentari", (req, res) -> {
			
			res.type("application/json");
			String payload = req.body();
			Komentar dto = jsonb.fromJson(payload, Komentar.class);
			
			
				
			Komentar kom = komentarService.kreirajKomentar(dto);
			
			if (kom!=null ) {
				res.status(200);
				return "Uspeh.";
			}
			res.status(400);
			return "Greska.";
		});
		
		get("/komentari/manifestacija/svi/:id", (req, res) -> {
			return jsonb.toJson(komentarService.preuzmiSveZaManifestaciju(req.params("id")));
		});
		
		get("/komentari/manifestacija/prihvaceni/:id", (req, res) -> {
			return jsonb.toJson(komentarService.preuzmiPrihvaceneZaManifestaciju(req.params("id")));
		});
		
		post("/komentari/prihvati/:id", (req, res) -> {
			String id = req.params("id");
			Komentar k = komentarService.preuzmiPoId(id);
			if (k == null) {
				res.status(400);
				return "Nepostojeci komentar.";
			}
				
			boolean uspesno = komentarService.prihvatiKomentar(id);
			
			if (uspesno) {
				res.status(200);
				return "Uspeh.";
			}
			res.status(400);
			return "Greska.";
		});
		
		post("/komentari/odbij/:id", (req, res) -> {
			String id = req.params("id");
			Komentar k = komentarService.preuzmiPoId(id);
			if (k == null) {
				res.status(400);
				return "Nepostojeci komentar.";
			}
				
			boolean uspesno = komentarService.odbijKomentar(id);
			
			if (uspesno) {
				res.status(200);
				return "Uspeh.";
			}
			res.status(400);
			return "Greska.";
		});
		
		
	}

}
