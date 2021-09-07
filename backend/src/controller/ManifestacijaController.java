package controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

import java.time.LocalDateTime;

import com.google.gson.Gson;

import domain.Manifestacija;
import helperClasses.CrudManifestacijaDTO;
import service.ManifestacijaService;

public class ManifestacijaController {
	private static Gson gson = new Gson();

	public ManifestacijaController() {
		ManifestacijaService manService = new ManifestacijaService();
		
		get("/manifestacije", (req, res) -> {
			return gson.toJson(manService.preuzmiSveNeobrisane());
		});
		
		get("/sveManifestacije", (req, res) -> {
			return gson.toJson(manService.preuzmiSve());
		});
		
		get("/manifestacije/:id", (req, res) -> {
			Manifestacija m = manService.preuzmiPoId(req.params("id"));
//			if (m != null) {
//				res.status(200);
//			}else {
//				res.status(404);
//			}
			return gson.toJson(m);
		});
		
		post("/manifestacije", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			CrudManifestacijaDTO dto = gson.fromJson(payload, CrudManifestacijaDTO.class);
			
			//provera tokena
			
			if (dto.getNaziv() == null || dto.getDatum() == null || dto.getBrojMesta() == 0 || dto.getCena() == 0 || dto.getTip() == null || dto.getSlikaPath()==null) {
				res.status(400);
				return "Nedostaju podaci.";
			}else if (dto.getBrojMesta() <= 0 || dto.getCena() <= 0){
				res.status(400);
				return "Brojne vrednosti su negativne.";
			}else if (dto.getDatum().isBefore(LocalDateTime.now())){
				res.status(400);
				return "Datum je u proslosti.";
			}
			
			Manifestacija m = manService.napraviManifestaciju(dto);
			if (m == null) {
				res.status(400);
				return "Neuspesno kreiranje.";
			}
			
			res.status(200);
			return "Uspeh";
		});
		
		
		put("/manifestacije", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			CrudManifestacijaDTO dto = gson.fromJson(payload, CrudManifestacijaDTO.class);
			
			//provera tokena
			
			Manifestacija m = manService.preuzmiPoId(dto.getId());
			if (m == null) {
				res.status(400);
				return null;
			}
			
			if (dto.getNaziv() == null || dto.getDatum() == null || dto.getBrojMesta() == 0 || dto.getCena() == 0 || dto.getTip() == null || dto.getSlikaPath()==null) {
				res.status(400);
				return null;
			}else if (dto.getBrojMesta() <= 0 || dto.getCena() <= 0){
				res.status(400);
				return null;
			}else if (dto.getDatum().isBefore(LocalDateTime.now())){
				res.status(400);
				return null;
			}
			
			int razlikaUBrojuMesta = dto.getBrojMesta() - m.getBrojMesta();
			int noviBrojSlobodnih = m.getSlobodnaMesta() + razlikaUBrojuMesta;
			if (noviBrojSlobodnih < 0) {
				res.status(400);
				return null;
			}
			
			Manifestacija man = manService.izmeniManifestaciju(dto);
			if (man == null) {
				res.status(400);
				return "Neuspesna izmena.";
			}
			
			res.status(200);
			return "Uspeh";
		});
		
		
		delete("/manifestacije/:id", (req, res) -> {
			
			//provera tokena
			
			String id = req.params("id");
			
			Manifestacija m = manService.preuzmiPoId(id);
			if (m == null) {
				res.status(400);
				return null;
			}
			
			boolean obrisano = manService.obrisiManifestaciju(id);
			if (obrisano == false) {
				res.status(400);
				return "Neuspesno brisanje.";
			}
			
			res.status(200);
			return "Uspeh";
		});
		
	}

}

//public UserController(final UserService userService) {
//	get("/users", new Route() {
//		@Override
//		public Object handle(Request request, Response response) {
//		// process request
//		return userService.getAllUsers();
//		}
//	});
//	
//	// more routes
//	}
//}
