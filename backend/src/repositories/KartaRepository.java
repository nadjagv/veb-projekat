package repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Karta;

public class KartaRepository {
	private ArrayList<Karta> karte;
	private static KartaRepository instance = null;

	private KartaRepository() {
		karte = new ArrayList<Karta>();
	}
	

	private KartaRepository(ArrayList<Karta> karte) {
		super();
		this.karte = karte;
	}
	
	public static KartaRepository getInstance() {
		if (instance == null) {
			instance = new KartaRepository();
		}
		return instance;
	}


	public ArrayList<Karta> getKarte() {
		return karte;
	}

	public void setKarte(ArrayList<Karta> karte) {
		this.karte = karte;
	}
	
	public Karta getOneById(String id) {
		return karte.stream()
				.filter(karta -> karta.getId().contentEquals(id))
				.findAny()
				.orElse(null);
	}
	
	public static void loadData() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();
	

		try {
			instance = mapper.readValue(new File("resources/Karte.json"), KartaRepository.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
