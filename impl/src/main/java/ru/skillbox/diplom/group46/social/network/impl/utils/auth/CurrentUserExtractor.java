package ru.skillbox.diplom.group46.social.network.impl.utils.auth;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.UserDTO;
import org.springframework.security.oauth2.jwt.Jwt;


import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrentUserExtractor {


    private static JwtUserExtractor extractor;
    public static JwtUserExtractor staticExtractor;

    @PostConstruct
    private void initExtractor() {
        staticExtractor = extractor;
    }

    @Deprecated
    public static UserDTO getCurrentUser() {
        log.debug("Method getCurrentUser() started");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return staticExtractor.getUserFromToken(((Jwt) authentication.getPrincipal()).getTokenValue());
    }

    public static UserDTO getCurrentUserFromAuthentication() {
        log.debug("Method getCurrentUser() started");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return extractor.getUserFromAuthentication(authentication);
    }

    public static UserDTO getUserFromJwt(Jwt jwt) {
        UUID id = UUID.fromString(jwt.getClaim("sub"));
        String username = jwt.getClaim("firstName");
        String email = jwt.getClaim("email");
        return new UserDTO(id, username, null, email);
    }
}
