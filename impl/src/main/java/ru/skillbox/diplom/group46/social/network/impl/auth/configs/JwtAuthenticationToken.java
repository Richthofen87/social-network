package ru.skillbox.diplom.group46.social.network.impl.auth.configs;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import ru.skillbox.diplom.group46.social.network.domain.user.User;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;
    private final User user;

    public JwtAuthenticationToken(User user, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        this.token = token;
    }

    public static JwtAuthenticationToken authenticated(User user, String token, Collection<? extends GrantedAuthority> authorities) {
        return new JwtAuthenticationToken(user, token, authorities);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}
