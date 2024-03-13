package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.PasswordChangeDto;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordChangeService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ResponseEntity<String> changePassword(PasswordChangeDto passwordChangeDto) {
        log.debug("Changing password for user: {}", CurrentUserExtractor.getCurrentUserFromAuthentication().getEmail());

        String newPassword1 = passwordChangeDto.getNewPassword1();
        String newPassword2 = passwordChangeDto.getNewPassword2();

        if (!newPassword1.equals(newPassword2)) {
            return ResponseEntity.badRequest().body("Введённые пароли не совпадают");
        }

        UUID uuid = CurrentUserExtractor.getCurrentUserFromAuthentication().getId();
        User currentUser = userRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("User with id: " + uuid + " not found"));

        String hashedPassword = passwordEncoder.encode(newPassword1);

        currentUser.setPassword(hashedPassword);

        userRepository.save(currentUser);


        return ResponseEntity.ok("Пароль успешно изменён");
    }

}
