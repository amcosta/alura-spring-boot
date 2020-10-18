package br.com.alura.forum.security;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expirateDate;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String create(Authentication authenticate) {
        Usuario user = (Usuario) authenticate.getPrincipal();
        Date data = new Date();
        Date expirate = new Date(data.getTime() + Long.parseLong(expirateDate));

        return Jwts.builder()
                .setIssuer("API Forum")
                .setSubject(user.getId().toString())
                .setIssuedAt(data)
                .setExpiration(expirate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
