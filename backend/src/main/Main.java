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

import java.io.File;
import java.io.IOException;

import repositories.AdministratorRepository;
import repositories.KartaRepository;
import repositories.KomentarRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;
import repositories.ProdavacRepository;

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
		

	}

}
