package ru.skillbox.diplom.group46.social.network.impl.config.audit;

import lombok.RequiredArgsConstructor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.skillbox.diplom.group46.social.network.domain.base.audit.UserJsonType;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;

import java.util.Optional;
import java.util.UUID;

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
            UUID uuid = CurrentUserExtractor.getCurrentUser().getId();
            String email = CurrentUserExtractor.getCurrentUser().getEmail();
            UserJsonType user = new UserJsonType(uuid.toString(), email);
            return Optional.of(user);
        }
    }


}
