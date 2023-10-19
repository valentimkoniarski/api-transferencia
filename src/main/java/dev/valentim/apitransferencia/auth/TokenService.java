package dev.valentim.apitransferencia.auth;

import dev.valentim.usuario.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Authentication authentication) {

        Usuario logged = (Usuario) authentication.getPrincipal();

        if (Boolean.FALSE.equals(logged.getEnabled())) {
            throw new IllegalStateException("user disabled");
        }

        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API")
                .setSubject(logged.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}