package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.ChangeEmailDto;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailChangeService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Transactional
    public ResponseEntity<String> changeEmail(ChangeEmailDto changeEmailDto) {

        UUID uuid = CurrentUserExtractor.getCurrentUser().getId();
        User currentUser = userRepository.findById(uuid).orElse(null);

        if (currentUser == null) {
            log.error("Current user not found");
            return ResponseEntity.badRequest().body("Текущий пользователь не найден");
        }

        log.debug("Changing email for user: {}", currentUser.getEmail());

        String newEmail = changeEmailDto.getEmail().getEmail();

        if (userRepository.existsByEmail(newEmail)) {
            log.error("Email already exists: {}", newEmail);

            return ResponseEntity.badRequest().body("Адрес электронной почты уже используется другим пользователем");
        }

        currentUser.setEmail(newEmail);
        userRepository.save(currentUser);

        log.debug("Email successfully changed for user: {}", newEmail);

        return ResponseEntity.ok("Адрес электронной почты успешно изменен. Проверьте свою почту для завершения операции.");
    }
}
