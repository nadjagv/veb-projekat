package controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import domain.Manifestacija;
import enums.Uloga;
import helperClasses.CrudManifestacijaDTO;
import repositories.ManifestacijaRepository;
import service.ManifestacijaService;
import utils.TokenUtils;

public class ManifestacijaController {
	Jsonb jsonb = JsonbBuilder.newBuilder().build();

	public ManifestacijaController() {
		ManifestacijaService manService = new ManifestacijaService();
		
		get("/manifestacije", (req, res) -> {
			return jsonb.toJson(manService.preuzmiSveNeobrisane());
		});
		
		get("/sveManifestacije", (req, res) -> {
			return jsonb.toJson(manService.preuzmiSve());
		});
		
		get("/manifestacije/:id", (req, res) -> {
			Manifestacija m = manService.preuzmiPoId(req.params("id"));
//			if (m != null) {
//				res.status(200);
//			}else {
//				res.status(404);
//			}
			return jsonb.toJson(m);
		});
		
		post("/manifestacije", (req, res) -> {
			Uloga uloga = TokenUtils.proveriToken(req);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}else if (uloga != Uloga.PRODAVAC) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			
			res.type("application/json");
			String payload = req.body();
			CrudManifestacijaDTO dto = jsonb.fromJson(payload, CrudManifestacijaDTO.class);
			
			//provera tokena
			if (dto.getNaziv() == null || dto.getDatumVremeOdrzavanja() == null || dto.getBrojMesta() == 0 || dto.getCenaRegular() == 0 || dto.getTip() == null) {
				res.status(400);
				return "Nedostaju podaci.";
			}else if (dto.getBrojMesta() <= 0 || dto.getCenaRegular() <= 0){
				res.status(400);
				return "Brojne vrednosti su negativne.";
			}else if (dto.getDatumVremeOdrzavanja().isBefore(LocalDateTime.now())){
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
			
			Uloga uloga = TokenUtils.proveriToken(req);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}else if (uloga != Uloga.PRODAVAC) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			res.type("application/json");
			String payload = req.body();
			CrudManifestacijaDTO dto = jsonb.fromJson(payload, CrudManifestacijaDTO.class);
			
			//provera tokena
			
			Manifestacija m = manService.preuzmiPoId(dto.getId());
			if (m == null) {
				res.status(400);
				return null;
			}
			
			if (dto.getNaziv() == null || dto.getDatumVremeOdrzavanja()== null || dto.getBrojMesta() == 0 || dto.getCenaRegular() == 0 || dto.getTip() == null) {
				res.status(400);
				return null;
			}else if (dto.getBrojMesta() <= 0 || dto.getCenaRegular() <= 0){
				res.status(400);
				return null;
			}else if (dto.getDatumVremeOdrzavanja().isBefore(LocalDateTime.now())){
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
			Uloga uloga = TokenUtils.proveriToken(req);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}else if (uloga != Uloga.PRODAVAC && uloga != Uloga.ADMINISTRATOR) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			
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
		
		
		post("/manifestacije/aktiviraj/:id", (req, res) -> {
			Uloga uloga = TokenUtils.proveriToken(req);
			if (uloga == null) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}else if (uloga != Uloga.ADMINISTRATOR) {
				res.status(401);
				return "Nije dozvojen pristup.";
			}
			String id = req.params("id");
			
			Manifestacija m = manService.preuzmiPoId(id);
			if (m == null) {
				res.status(400);
				return "Neuspesno aktiviranje.";
			}
			if (manService.aktivirajManifestaciju(id)) {
				res.status(200);
				return "Uspeh";
			}
			
			res.status(400);
			return "Neuspesno aktiviranje.";
		});
		
		
		put("/manifestacije/slika/:id", (req, res) -> {
//			Uloga uloga = TokenUtils.proveriToken(req);
//			if (uloga == null) {
//				res.status(401);
//				return "Nije dozvojen pristup.";
//			}else if (uloga != Uloga.PRODAVAC) {
//				res.status(401);
//				return "Nije dozvojen pristup.";
//			}
			res.type( "multipart/form-data");
			String location = "image";          // the directory location where files will be stored
			long maxFileSize = 100000000;       // the maximum size allowed for uploaded files
			long maxRequestSize = 100000000;    // the maximum size allowed for multipart/form-data requests
			int fileSizeThreshold = 1024;       // the size threshold after which files will be written to disk
			String manifestacijaId = req.params("id");
			
			
			MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
			     location, maxFileSize, maxRequestSize, fileSizeThreshold);
			 req.raw().setAttribute("org.eclipse.jetty.multipartConfig",
			     multipartConfigElement);
			 
			String fName = req.raw().getPart("file").getSubmittedFileName();
			String[] tokeni = fName.split("\\.");
			String ext = tokeni[1];
			
			String filename = manifestacijaId + "." + ext;

			Part uploadedFile = req.raw().getPart("file");
			Path out = Paths.get("static/images/" + filename);
			try (final InputStream in = uploadedFile.getInputStream()) {
			   Files.copy(in, out);
			   uploadedFile.delete();
			}
			
			Manifestacija m = manService.preuzmiPoId(manifestacijaId);
			if (m == null) {
				res.status(400);
				return "Neuspesno uploadovanje.";
			}
			
			manService.promeniSliku(manifestacijaId, filename);
			

			return "OK";
			});
		
	}

}
