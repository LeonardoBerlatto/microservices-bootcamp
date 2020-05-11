package br.com.photoapp.api.usermanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.util.Optional.empty;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.secret}")
	private String tokenSecret;

	@Value("${security.jwt.expiration}")
	private int tokenExpiration;

	public String generateToken(Authentication authentication) {

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + tokenExpiration);

		return Jwts.builder()
				.setSubject(userPrincipal.getUser().getUsername())
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, tokenSecret)
				.compact();
	}

	public Optional<Long> getUserId(String jwt) {
		try {
			Claims claims = parse(jwt).getBody();

			return Optional.of(parseLong(claims.getSubject()));
		} catch (Exception exception) {
			return empty();
		}
	}

	private Jws<Claims> parse(String jwt) {
		return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(jwt);
	}
}
