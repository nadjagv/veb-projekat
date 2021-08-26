package main;

import static spark.Spark.get;
import static spark.Spark.port;

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
		
		get("/main/test", (req, res) -> {
			return "Doktor";
		});

	}

}
