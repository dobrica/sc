package com.upp.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Service
public class TokenUtils {

	@Value("uppapp")
	private String APP_NAME;

	@Value("somesecret")
	public String SECRET;

	@Value("3600")
	private int EXPIRES_IN;

	@Value("Authorization")
	private String AUTH_HEADER;

	@Autowired
	TimeProvider timeProvider;

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

		public String generateToken(String username
				) {
			return Jwts.builder()
					.setIssuer(APP_NAME)
					.setSubject(username)
					.setIssuedAt(timeProvider.now())
					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		}

		public String refreshToken(String token
				) {
			String refreshedToken;
			try {
				final Claims claims = this.getAllClaimsFromToken(token);
				claims.setIssuedAt(timeProvider.now());
				refreshedToken = Jwts.builder()
						.setClaims(claims)
						.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
			} catch (Exception e) {
				refreshedToken = null;
			}
			return refreshedToken;
		}

		public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
			final Date created = this.getIssuedAtDateFromToken(token);
			return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
					&& (!(this.isTokenExpired(token))
							));
		}

		public Boolean validateToken(String token, UserDetails userDetails) {

			final String username = getUsernameFromToken(token);
			final Date created = getIssuedAtDateFromToken(token);

			return (username != null && username.equals(userDetails.getUsername()));
		}

		private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
			return (lastPasswordReset != null && created.before(lastPasswordReset));
		}

		private Boolean isTokenExpired(String token) {
			final Date expiration = this.getExpirationDateFromToken(token);
			return expiration.before(timeProvider.now());
		}

		private Claims getAllClaimsFromToken(String token) {
			Claims claims;
			try {
				claims = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(token)
						.getBody();
			} catch (Exception e) {
				claims = null;
			}
			return claims;
		}

		public String getUsernameFromToken(String token) {
			String username;
			try {
				final Claims claims = this.getAllClaimsFromToken(token);
				username = claims.getSubject();
			} catch (Exception e) {
				username = null;
			}
			return username;
		}

		public Date getIssuedAtDateFromToken(String token) {
			Date issueAt;
			try {
				final Claims claims = this.getAllClaimsFromToken(token);
				issueAt = claims.getIssuedAt();
			} catch (Exception e) {
				issueAt = null;
			}
			return issueAt;
		}

		public String getAudienceFromToken(String token) {
			String audience;
			try {
				final Claims claims = this.getAllClaimsFromToken(token);
				audience = claims.getAudience();
			} catch (Exception e) {
				audience = null;
			}
			return audience;
		}

		public Date getExpirationDateFromToken(String token) {
			Date expiration;
			try {
				final Claims claims = this.getAllClaimsFromToken(token);
				expiration = claims.getExpiration();
			} catch (Exception e) {
				expiration = null;
			}
			return expiration;
		}

		public String getToken(HttpServletRequest request) {
			String authHeader = getAuthHeaderFromHeader(request);

			
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				return authHeader.substring(7);
			}

			return null;
		}

		public String getAuthHeaderFromHeader(HttpServletRequest request) {
			return request.getHeader(AUTH_HEADER);
		}

}