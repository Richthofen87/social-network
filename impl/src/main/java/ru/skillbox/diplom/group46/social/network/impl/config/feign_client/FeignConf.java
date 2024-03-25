package ru.skillbox.diplom.group46.social.network.impl.config.feign_client;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
@RequiredArgsConstructor
public class FeignConf {

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return template -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String jwt = ((Jwt) authentication.getPrincipal()).getTokenValue();
            template.header("Authorization",
                    "Bearer ".concat(jwt));
        };
    }
}
