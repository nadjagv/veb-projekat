package service;

import java.util.ArrayList;

import domain.Administrator;
import repositories.AdministratorRepository;

public class AdministratorService {

	AdministratorRepository administratorRep;

	public AdministratorService() {
		administratorRep = AdministratorRepository.getInstance();
	}
	
	public ArrayList<Administrator> preuzmiSve() {
		return administratorRep.getAdministratori();
	}
	
	public Administrator preuzmiPoUsername(String username) {
		return administratorRep.getOneByUsername(username);
	}
	
	

}
