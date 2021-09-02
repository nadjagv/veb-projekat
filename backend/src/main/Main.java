package main;

import static spark.Spark.port;

import controller.AdministratorController;
import controller.KartaController;
import controller.KomentarController;
import controller.KupacController;
import controller.ManifestacijaController;
import controller.ProdavacController;
import repositories.AdministratorRepository;
import repositories.KartaRepository;
import repositories.KomentarRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;
import repositories.ProdavacRepository;

public class Main {

	public static void main(String[] args) {
		
		AdministratorRepository.loadData();
		KupacRepository.loadData();
		KartaRepository.loadData();
		ProdavacRepository.loadData();
		ManifestacijaRepository.loadData();
		KomentarRepository.loadData();
		
		port(8080);
		
		new AdministratorController();
		new KupacController();
		new ProdavacController();
		new KartaController();
		new ManifestacijaController();
		new KomentarController();

	}

}
