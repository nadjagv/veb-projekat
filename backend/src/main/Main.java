package main;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

import controller.AdministratorController;
import controller.KartaController;
import controller.KomentarController;
import controller.KorisnikController;
import controller.KupacController;
import controller.ManifestacijaController;
import controller.ProdavacController;
import domain.Administrator;
import domain.Manifestacija;
import enums.TipManifestacije;
import helperClasses.CrudManifestacijaDTO;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import repositories.AdministratorRepository;
import repositories.KartaRepository;
import repositories.KomentarRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;
import repositories.ProdavacRepository;
import service.ManifestacijaService;

public class Main {

	public static void main(String[] args) throws IOException {
		
		AdministratorRepository.loadData();
		KupacRepository.loadData();
		KartaRepository.loadData();
		ProdavacRepository.loadData();
		ManifestacijaRepository.loadData();
		KomentarRepository.loadData();
		
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		new KorisnikController();
		new AdministratorController();
		new KupacController();
		new ProdavacController();
		new KartaController();
		new ManifestacijaController();
		new KomentarController();
		
//		CrudManifestacijaDTO test = new CrudManifestacijaDTO("naz", "NS", "srb", "55536", "bulevar", "5", TipManifestacije.PREDSTAVA, LocalDateTime.MAX.minusHours(5), 22221212.0, 333, "putagdjd");
//		
//		CrudManifestacijaDTO testdf = new CrudManifestacijaDTO("nazeyey", "NS", "srb", "55536", "bulevar", "5", TipManifestacije.PREDSTAVA, LocalDateTime.MAX.minusHours(5), 22221212.0, 333, "putagdjd");
//		
//		
//		ManifestacijaService service = new ManifestacijaService();
//		Manifestacija m = service.napraviManifestaciju(test);
//		Manifestacija msff = service.napraviManifestaciju(testdf);
//		//test.setId(m.getId());
//		//test.setNaziv("novi naziv izmena");
//		//service.izmeniManifestaciju(test);
	
		
		

	}

}
