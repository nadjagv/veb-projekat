package repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import domain.Komentar;
import domain.Kupac;

public class KupacRepository {
	private ArrayList<Kupac> kupci;
	private static KupacRepository instance = null;

	private KupacRepository() {
		kupci = new ArrayList<Kupac>();
	}

	private KupacRepository(ArrayList<Kupac> kupci) {
		super();
		this.kupci = kupci;
	}
	public static KupacRepository getInstance() {
		if (instance == null) {
			instance = new KupacRepository();
		}
		return instance;
	}

	public ArrayList<Kupac> getKupci() {
		return kupci;
	}

	public void setKupci(ArrayList<Kupac> kupci) {
		this.kupci = kupci;
	}
	
	public Kupac getOneByUsername(String username) {
		return kupci.stream()
				.filter(kupac -> kupac.getUsername().equals(username))
				.findAny()
				.orElse(null);
	}
	
	public static void loadData() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();
	

		try {
			instance = mapper.readValue(new File("resources/Kupci.json"), KupacRepository.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void add(Kupac k) {
		kupci.add(k);
		save();
	}
	
	public void save(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			mapper.writeValue(new File("resources/Kupci.json"), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
