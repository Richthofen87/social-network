package ru.skillbox.diplom.group46.social.network.impl.config.web_socket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import ru.skillbox.diplom.group46.social.network.impl.service.web_socket.HandshakeHandler;
import ru.skillbox.diplom.group46.social.network.impl.service.web_socket.WebSocketHandler;

/**
 * WebSocketConfiguration
 *
 * @author vladimir.sazonov
 */

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;
    private final HandshakeHandler handshakeHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/api/v1/streaming/ws")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(handshakeHandler);
    }
}