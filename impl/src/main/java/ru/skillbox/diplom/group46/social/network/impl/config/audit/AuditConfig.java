package ru.skillbox.diplom.group46.social.network.impl.config.audit;

import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.skillbox.diplom.group46.social.network.domain.base.audit.UserJsonType;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class AuditConfig {

    @Bean
    public AuditorAware<UserJsonType> auditorProvider() {
        return new EntityAuditorAware();
    }

    public class EntityAuditorAware implements AuditorAware<UserJsonType> {
        @Override
        public Optional<UserJsonType> getCurrentAuditor() {
            UserJsonType user;
            try {
                UUID uuid = CurrentUserExtractor.getCurrentUserFromAuthentication().getId();
                String email = CurrentUserExtractor.getCurrentUserFromAuthentication().getEmail();
                user = new UserJsonType(uuid.toString(), email);
            } catch (NullPointerException e) {
                log.info("Current user is null");
                return Optional.empty();
            }
            return Optional.of(user);
        }
    }
}
