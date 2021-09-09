package domain;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;

public class Otkazivanje {
	@JsonbDateFormat(JsonbDateFormat.TIME_IN_MILLIS)
	LocalDateTime datum;
	String kupacUsername;
	
	public Otkazivanje() {
	}
	
	public Otkazivanje(LocalDateTime datum, String kupacUsername) {
		super();
		this.datum = datum;
		this.kupacUsername = kupacUsername;
	}
	
	public LocalDateTime getDatum() {
		return datum;
	}
	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}
	public String getKupacUsername() {
		return kupacUsername;
	}
	public void setKupacUsername(String kupacUsername) {
		this.kupacUsername = kupacUsername;
	}
	
	

}
