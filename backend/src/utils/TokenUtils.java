package utils;

import java.security.Key;
import java.util.Date;

import domain.Korisnik;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import spark.Request;

public class TokenUtils {
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public TokenUtils() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void napraviToken(Korisnik k) {
		String jws = Jwts.builder().setSubject(k.getUsername()).setExpiration(new Date(new Date().getTime() + 1000*10*60)).setIssuedAt(new Date()).signWith(key).compact();
		k.setJWTToken(jws);
	}
	
	public static boolean proveriToken(Request req) {
		String auth = req.headers("Authorization");
		System.out.println("Authorization: " + auth);
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
			    // ako nije bacio izuzetak, onda je OK
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	

}
