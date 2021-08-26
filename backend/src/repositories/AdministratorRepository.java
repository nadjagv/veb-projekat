package repositories;

import java.util.ArrayList;

import domain.Administrator;

public class AdministratorRepository {
	private ArrayList<Administrator> administratori;

	public AdministratorRepository() {
		administratori = new ArrayList<Administrator>();
	}

	public AdministratorRepository(ArrayList<Administrator> administratori) {
		super();
		this.administratori = administratori;
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

}
