package utils;

import java.security.Key;
import java.util.Date;

import com.fasterxml.jackson.core.PrettyPrinter;

import domain.Korisnik;
import enums.Uloga;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repositories.AdministratorRepository;
import repositories.KartaRepository;
import repositories.KupacRepository;
import repositories.ManifestacijaRepository;
import repositories.ProdavacRepository;
import spark.Request;

public class TokenUtils {
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	static AdministratorRepository administratorRep = AdministratorRepository.getInstance();
	static KupacRepository kupacRep = KupacRepository.getInstance();
	static ProdavacRepository prodavacRep = ProdavacRepository.getInstance();

	public TokenUtils() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void napraviToken(Korisnik k) {
		String jws = Jwts.builder().setSubject(k.getUsername()).setExpiration(new Date(new Date().getTime() + 1000*10*60)).setIssuedAt(new Date()).signWith(key).compact();
		k.setJWTToken(jws);
	}
	
	public static Uloga proveriToken(Request req) {
		String auth = req.headers("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
			    // ako nije bacio izuzetak, onda je OK
			    String username = claims.getBody().getSubject();
			    Korisnik k = pretraziPoUsername(username);
				return k.getUloga();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
		return null;
	}
	
	
	public static Korisnik pretraziPoUsername(String username) {
		Korisnik pronadjen;
		pronadjen = kupacRep.getOneByUsername(username);
		if (pronadjen != null && !pronadjen.isObrisan()) {
			return pronadjen;
		}
		pronadjen = prodavacRep.getOneByUsername(username);
		if (pronadjen != null && !pronadjen.isObrisan()) {
			return pronadjen;
		}
		pronadjen = administratorRep.getOneByUsername(username);
		if (pronadjen != null && !pronadjen.isObrisan()) {
			return pronadjen;
		}
		
		
		return null;
	}
	
	

}
