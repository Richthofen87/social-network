package ru.skillbox.diplom.group46.social.network.impl.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.UserDTO;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.service.account.AccountService;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;

    @Transactional
    public User createNewUser(RegistrationDto registrationDto) {
        log.debug("Method createNewUser(%s) started with param: \"%s\"".formatted(RegistrationDto.class, registrationDto));
        return userRepository.findById(accountService.createNewAccount(registrationDto).getId()).get();
    }

    public User findByEmail(String email) {
        log.debug("Method findByEmail(%s) started with param: \"%s\"".formatted(String.class, email));
        return userRepository.findByEmail(email).orElse(null);
    }

    public User convertUserDtoToUser(UserDTO userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return user;
    }
}