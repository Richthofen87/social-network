package ru.skillbox.diplom.group46.social.network.impl.service.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.SignupDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.UserDTO;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.account.AccountService;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.JwtUserExtractor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;

    @Transactional
    public User createNewUser(SignupDTO signupDTO) {
        log.debug("Method createNewUser(%s) started with param: \"%s\"".formatted(SignupDTO.class, signupDTO));
        return userRepository.findById(accountService.createNewAccount(signupDTO).getId()).get();
    }

    public User findById(String id) {
        log.debug("Method findById(%s) started with param: \"%s\"".formatted(String.class, id));
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("user with id: \"%s\" not found".formatted(id)));
    }

    public User findByEmail(String email) {
        log.debug("Method findByEmail(%s) started with param: \"%s\"".formatted(String.class, email));
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDetails loadUserByUsername(String username) {
        log.debug("Method loadUserByUsername(%s) started with param: \"%s\"".formatted(String.class, username));
        User user;
        try {
            user = userRepository.findByFirstName(username);
        } catch (EntityNotFoundException ex) {
            throw new UsernameNotFoundException("User with name: \"%s\" not found".formatted(username), ex);
        }
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getValue()))
                .collect(Collectors.toSet());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getFirstName())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}