package podesavanja;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.TipKupca;

public class Podesavanja {
	private TipKupca srebrni, zlatni, bronzani;
	private static Podesavanja instance = null;

	private Podesavanja() {
		// TODO Auto-generated constructor stub
	}

	private Podesavanja(TipKupca srebrni, TipKupca zlatni, TipKupca bronzani) {
		super();
		this.srebrni = srebrni;
		this.zlatni = zlatni;
		this.bronzani = bronzani;
	}

	public static Podesavanja getInstance() {
		if (instance == null) {
			instance = new Podesavanja();
		}
		return instance;
	}

	public TipKupca getSrebrni() {
		return srebrni;
	}

	public void setSrebrni(TipKupca srebrni) {
		this.srebrni = srebrni;
	}

	public TipKupca getZlatni() {
		return zlatni;
	}

	public void setZlatni(TipKupca zlatni) {
		this.zlatni = zlatni;
	}
	
	public TipKupca getBronzani() {
		return bronzani;
	}

	public void setBronzani(TipKupca bronzani) {
		this.bronzani = bronzani;
	}

	public static void loadData() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.findAndRegisterModules();
	

		try {
			instance = mapper.readValue(new File("resources/Podesavanja.json"), Podesavanja.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	

}
