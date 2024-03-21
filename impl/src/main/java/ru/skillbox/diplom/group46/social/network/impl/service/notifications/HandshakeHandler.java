package ru.skillbox.diplom.group46.social.network.impl.service.notifications;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * HandshakeHandler
 *
 * @author vladimir.sazonov
 */

@Component
public class HandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        Principal principal = request.getPrincipal();
        attributes.put("userId", ((JwtAuthenticationToken) principal).getTokenAttributes().get("sub"));
        return principal;
    }
}
