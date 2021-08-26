package main;

import static spark.Spark.get;
import static spark.Spark.port;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Administrator;
import domain.Karta;
import domain.Komentar;
import domain.Lokacija;
import domain.Manifestacija;
import enums.Pol;
import enums.Uloga;
import repositories.AdministratorRepository;
import repositories.KartaRepository;
import repositories.KomentarRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;
import repositories.ProdavacRepository;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		port(8080);
		
		get("/main/test", (req, res) -> {
			return "Doktor";
		});
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();

		AdministratorRepository adminRep = null;
		KupacRepository kupacRep = null;
		KartaRepository kartaRep = null;
		ProdavacRepository prodavacRep = null;
		ManifestacijaRepository manifestacijaRep = null;
		KomentarRepository komentarRep = null;
		try {
			adminRep = mapper.readValue(new File("resources/Administratori.json"), AdministratorRepository.class);
			kupacRep = mapper.readValue(new File("resources/Kupci.json"), KupacRepository.class);
			prodavacRep = mapper.readValue(new File("resources/Prodavci.json"), ProdavacRepository.class);
			kartaRep = mapper.readValue(new File("resources/Karte.json"), KartaRepository.class);
			manifestacijaRep = mapper.readValue(new File("resources/Manifestacije.json"), ManifestacijaRepository.class);
			komentarRep = mapper.readValue(new File("resources/Komentari.json"), KomentarRepository.class);
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
