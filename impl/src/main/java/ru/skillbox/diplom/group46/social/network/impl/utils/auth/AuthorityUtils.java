package ru.skillbox.diplom.group46.social.network.impl.utils.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.skillbox.diplom.group46.social.network.domain.user.role.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AuthorityUtils {

    public static Collection<? extends GrantedAuthority> convertRolesToAuthorities(Collection<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getValue()));
            }
        }
        return authorities;
    }
}
