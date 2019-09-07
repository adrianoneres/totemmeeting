package br.com.totemti.totemmeeting.util;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtil {
	
	private static final String SECRET_KEY = "totemmeetingultrasecretkey";
	
	public static String createToken(Long id) {
		Date now = new Date();
		
		return Jwts.builder()
				.setSubject(id.toString())
				.setIssuedAt(now)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	public static Long getUserIdFromSubject(String requestToken) {
		if (!requestToken.startsWith("Bearer")) {
			throw new IllegalArgumentException("Invalid authentication token.");
		}
		
		String token = requestToken.substring(7);
		
		String subject = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
		return Long.parseLong(subject);
	}

}
