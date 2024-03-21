package ru.skillbox.diplom.group46.social.network.impl.auth.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.skillbox.diplom.group46.social.network.impl.auth.security.JwtRequestWrapper;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("Method doFilterInternal() started");

        String authHeader = request.getHeader("Authorization");
        log.debug("Authorization header: {}", authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ") && !authHeader.contains("undefined")) {
            log.debug("Authorization header is present. Bypassing filter.");
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = extractAccessTokenFromCookie(request);
        log.debug("JWT token extracted from cookie: {}", jwt);
        if (jwt != null) {
            try {
                BearerTokenAuthenticationToken bearerTokenAuthenticationToken = new BearerTokenAuthenticationToken(jwt);
                bearerTokenAuthenticationToken.setAuthenticated(true);
                log.debug("Bearer token: {}", jwt);

                Authentication authentication = jwtAuthenticationProvider.authenticate(bearerTokenAuthenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(new JwtRequestWrapper(request, jwt), response);
            } catch (Exception e) {
                log.error("Exception occurred during authentication", e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        if (jwt == null) {
            log.debug("Token not found in request body. Proceeding with filter chain.");
            filterChain.doFilter(request, response);
        }
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        log.debug("Method extractAccessTokenFromCookie() started");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    log.debug("JWT token found in cookie: {}", cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        log.debug("Token not found in cookies.");
        return null;
    }

}
