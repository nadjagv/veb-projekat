package repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Manifestacija;

public class ManifestacijaRepository {
	private ArrayList<Manifestacija> manifestacije;
	private static ManifestacijaRepository instance = null;

	private ManifestacijaRepository() {
		manifestacije = new ArrayList<Manifestacija>();
	}
	
	

	private ManifestacijaRepository(ArrayList<Manifestacija> manifestacije) {
		super();
		this.manifestacije = manifestacije;
	}
	
	public static ManifestacijaRepository getInstance() {
		if (instance == null) {
			instance = new ManifestacijaRepository();
		}
		return instance;
	}



	public ArrayList<Manifestacija> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(ArrayList<Manifestacija> manifestacije) {
		this.manifestacije = manifestacije;
	}
	
	public Manifestacija getOneById(String id) {
		return manifestacije.stream()
				.filter(manifestacija -> manifestacija.getId().contentEquals(id))
				.findAny()
				.orElse(null);
	}
	
	public static void loadData() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();
	

		try {
			instance = mapper.readValue(new File("resources/Manifestacije.json"), ManifestacijaRepository.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
