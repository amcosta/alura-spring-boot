package br.com.alura.forum.controller;

import br.com.alura.forum.dto.LoginForm;
import br.com.alura.forum.dto.TokenDto;
import br.com.alura.forum.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginForm form) {
        try {
            UsernamePasswordAuthenticationToken auth = form.convert();
            Authentication authenticate = this.authManager.authenticate(auth);
            String token = tokenService.create(authenticate);

            return ResponseEntity.ok().body(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
