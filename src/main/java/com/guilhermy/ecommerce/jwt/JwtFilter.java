package com.guilhermy.ecommerce.jwt;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, java.io.IOException {

        // Permitir endpoints públicos sem autenticação
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/auth/") ||
                requestURI.startsWith("/api/products/") ||
                requestURI.startsWith("/api/categories/") ||
                requestURI.startsWith("/api/kafka/") ||
                requestURI.startsWith("/swagger-ui.html") ||
                requestURI.startsWith("/swagger-ui/") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/api-docs") ||
                requestURI.startsWith("/swagger-resources") ||
                requestURI.startsWith("/webjars/")) {
            chain.doFilter(request, response);
            return;
        }
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            try {
                String token = header.substring(7);
                String email = jwtUtil.extractUsername(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails user = userDetailsService.loadUserByUsername(email);
                    if (jwtUtil.isValid(token, user)) {
                        String role = jwtUtil.extractRole(token); // você precisa implementar esse método
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch (Exception e) {
                // Se o token for inválido ou expirado, apenas continua sem autenticação
                // Isso permite que endpoints públicos funcionem
                logger.debug("Token JWT inválido ou expirado: " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
