package dev.xsenny.grades.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRET_KEY = "w5wCrNXZ7pfj8u7UXD6VHFx9pVmBgQfgFabNzn/oQ8QJwjEh65dmZQfwL6vN4HGTzT6dAQOA0PBCeSjl2/4agOvSsCLf9UYVDn0QBuOBzuc5hBB3gCZ1PYZdKEQrv4paQiX6OWDVJEfiTkINamisoFLVhXQ4lT50sZw/eo3PCv8bdjcJktwWnz3YeBKzCwKiXvUU8Npoisa5N5FzOBlz1Y0icCT+/olbPwitp7SQJCjyjCulqNOBO4r0th6a0foS9dvBkSojvhGo0rvX4AD1FifIkEiD+88sXeF03VsJx4ao5IeU+MX8NdkEIlqJxVPDz6TgqQh1Tidm/x7VWNDiic38cgT9euf7yfUGUITlqFU=";

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60000 * 60 * 24 * 5))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
