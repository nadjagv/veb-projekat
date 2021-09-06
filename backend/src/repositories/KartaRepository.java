package repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
	
	public void add(Karta k) {
		karte.add(k);
		save();
	}
	
	public void save(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			mapper.writeValue(new File("resources/Karte.json"), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
