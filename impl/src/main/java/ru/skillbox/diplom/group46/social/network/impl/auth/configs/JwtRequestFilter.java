package ru.skillbox.diplom.group46.social.network.impl.auth.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.auth.security.JwtRequestWrapper;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.JwtUserExtractor;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtUserExtractor jwtUserExtractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        if (authHeader != null && authHeader.startsWith("Bearer ") && !authHeader.contains("undefined")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = extractAccessTokenFromCookie(request);
        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = jwtUserExtractor.getUserFromToken(jwt);
            Collection<? extends GrantedAuthority> authorities = getAuthoritiesFromDummyRoles();
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken
                    (
                            user, jwt, authorities
                    );
            BearerTokenAuthenticationToken bearerTokenAuthenticationToken = new BearerTokenAuthenticationToken(jwt);
            bearerTokenAuthenticationToken.setAuthenticated(true);
            Authentication authentication = jwtAuthenticationProvider.authenticate(bearerTokenAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(new JwtRequestWrapper(request, jwt), response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthoritiesFromDummyRoles() {
        // Создаем заглушку ролей
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        // Добавьте другие роли по необходимости
        return authorities;
    }
}