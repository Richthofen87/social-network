package ru.skillbox.diplom.group46.social.network.impl.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class JwtRequestWrapper extends HttpServletRequestWrapper {
    private String jwt;

    public JwtRequestWrapper(HttpServletRequest request, String jwt) {
        super(request);
        this.jwt = jwt;
    }

    @Override
    public String getHeader(String name) {
        if (name.equalsIgnoreCase("Authorization"))
            return "Bearer ".concat(jwt);
        return super.getHeader(name);
    }
}
