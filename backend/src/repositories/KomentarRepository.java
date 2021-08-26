package repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Komentar;

public class KomentarRepository {
	private ArrayList<Komentar> komentari;
	private static KomentarRepository instance = null;

	private KomentarRepository() {
		komentari = new ArrayList<Komentar>();
	}

	private KomentarRepository(ArrayList<Komentar> komentari) {
		super();
		this.komentari = komentari;
	}
	
	public static KomentarRepository getInstance() {
		if (instance == null) {
			instance = new KomentarRepository();
		}
		return instance;
	}

	public ArrayList<Komentar> getKomentari() {
		return komentari;
	}

	public void setKomentari(ArrayList<Komentar> komentari) {
		this.komentari = komentari;
	}
	
	public Komentar getOneById(String id) {
		return komentari.stream()
				.filter(komentar -> komentar.getId().contentEquals(id))
				.findAny()
				.orElse(null);
	}
	
	public static void loadData() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();
	

		try {
			instance = mapper.readValue(new File("resources/Komentari.json"), KomentarRepository.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
