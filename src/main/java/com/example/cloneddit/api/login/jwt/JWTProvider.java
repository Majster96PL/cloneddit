package com.example.cloneddit.api.login.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
@Slf4j
@RequiredArgsConstructor
public class JWTProvider {

    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpiration;


    @PostConstruct
    public void init() throws IOException {
        try{
            keyStore = KeyStore.getInstance("JKS");
            InputStream inputStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(inputStream, "secret".toCharArray());
        }catch(KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
           throw new IOException(e);
        }
    }

    public String tokenGenerated(Authentication authentication)
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        org.springframework.security.core.userdetails.User
                userPrincipal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getKey())
                .setExpiration(Date.from(Instant.now().plusMillis(getJWTExpiration())))
                .compact();
    }

    public String generatedRefreshToken(String username)
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getKey())
                .setExpiration(Date.from(Instant.now().plusMillis(getJWTExpiration())))
                .compact();
    }

   private PrivateKey getKey()
           throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return (PrivateKey)  keyStore.getKey("springblog", "secret".toCharArray());
    }

    public boolean validateToken(String jwt) throws KeyStoreException {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }
    
    private PublicKey getPublicKey() throws KeyStoreException{
        return keyStore.getCertificate("springblog").getPublicKey();
    }

    public String getUsernameFromToken(String token) throws KeyStoreException {
      Claims claims = parser()
              .setSigningKey(getPublicKey())
              .parseClaimsJws(token)
              .getBody();
      return claims.getSubject();
    }

    public Long getJWTExpiration(){
        return jwtExpiration;
    }
}
