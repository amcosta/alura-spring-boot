package br.com.alura.forum.security;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.h2.engine.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthViaToken extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository userRepository;

    public AuthViaToken(TokenService tokenService, UsuarioRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = this.getToken(request);

        if (!tokenService.isValide(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userid = tokenService.getUserId(token);
        Optional<Usuario> usuario = userRepository.findById(userid);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                usuario.get(),
                null,
                usuario.get().getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}
