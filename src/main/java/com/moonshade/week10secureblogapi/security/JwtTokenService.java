package com.moonshade.week10secureblogapi.security;

import com.moonshade.week10secureblogapi.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.JwtSignatureValidator;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.xml.crypto.AlgorithmMethod;
import java.io.Serializable;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/*
    This class is responsible for processing the token passed in for authentication. Or for token generation
    and validation
 */

/*
    What is a Signin key? It is a secret used to digitally sign the JWT. It is used to create the signature part
    of the jwt token which is used to verify that the sender of  the jwt sender is who they claim to be and ensure
    that the message was not changed along the way.
    The signin key is used with the signin algorithm specified in the jwt header to create the signature. The specific
    sign in algorithm and key size will depend on the security requirement of the application and the level of trust in the
    sign in party.
 */
@Service
@Slf4j
public class JwtTokenService implements Serializable {
    //set the time for token to last
    private static final Duration expirationTime = Duration.ofMinutes(120);
    @Value("${jwt.secret}")
    private String secret;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        log.info("Getting username from token");
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte [] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //this method is used to extract any claim from token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //check if the token has expired
    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    /*
    TO CREATE THE TOKEN
    1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    2. Sign the JWT using the HS256 algorithm and secret key.
    3. compact the token into a string.
    */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationTime.toMillis()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    //Generate a token without claims
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    //validate token by comparing if the passed in(user) token is same as the one in the database
    //check also if the token is not expired.
    public boolean validateToken(String token, UserDetails userDetails) {
        log.info("Getting username from token");
        final String username = getUsernameFromToken(token);
        log.info("Checking if username equals UserDB details and if token is not expired");
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
