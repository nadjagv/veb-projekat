package repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Administrator;

public class AdministratorRepository {
	private ArrayList<Administrator> administratori;
	private static AdministratorRepository instance = null;

	private AdministratorRepository() {
		administratori = new ArrayList<Administrator>();
	}

	private AdministratorRepository(ArrayList<Administrator> administratori) {
		super();
		this.administratori = administratori;
	}
	
	public static AdministratorRepository getInstance() {
		if (instance == null) {
			instance = new AdministratorRepository();
		}
		return instance;
	}

	public ArrayList<Administrator> getAdministratori() {
		return administratori;
	}

	public void setAdministratori(ArrayList<Administrator> administratori) {
		this.administratori = administratori;
	}
	
	public Administrator getOneByUsername(String username) {
		return administratori.stream()
				.filter(admin -> admin.getUsername().contentEquals(username))
				.findAny()
				.orElse(null);
	}
	
	public static void loadData() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();
	

		try {
			instance = mapper.readValue(new File("resources/Administratori.json"), AdministratorRepository.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
