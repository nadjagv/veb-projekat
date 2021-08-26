package repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Prodavac;

public class ProdavacRepository {
	private ArrayList<Prodavac> prodavci;
	private static ProdavacRepository instance = null;

	private ProdavacRepository() {
		prodavci = new ArrayList<Prodavac>();
	}

	private ProdavacRepository(ArrayList<Prodavac> prodavci) {
		super();
		this.prodavci = prodavci;
	}
	
	public static ProdavacRepository getInstance() {
		if (instance == null) {
			instance = new ProdavacRepository();
		}
		return instance;
	}

	public ArrayList<Prodavac> getProdavci() {
		return prodavci;
	}

	public void setProdavci(ArrayList<Prodavac> prodavci) {
		this.prodavci = prodavci;
	}
	
	public Prodavac getOneByUsername(String username) {
		return prodavci.stream()
				.filter(prodavac -> prodavac.getUsername().contentEquals(username))
				.findAny()
				.orElse(null);
	}
	
	public static void loadData() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();
	

		try {
			instance = mapper.readValue(new File("resources/Prodavci.json"), ProdavacRepository.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
