package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.LoginDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.SignupDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.TokenDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.UserDTO;
import ru.skillbox.diplom.group46.social.network.api.exception.auth.AppError;
import ru.skillbox.diplom.group46.social.network.api.service.auth.AuthService;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.domain.user.role.Role;
import ru.skillbox.diplom.group46.social.network.impl.auth.configs.JwtAuthenticationToken;
import ru.skillbox.diplom.group46.social.network.impl.auth.security.TokenGenerator;
import ru.skillbox.diplom.group46.social.network.impl.service.role.RoleService;
import ru.skillbox.diplom.group46.social.network.impl.service.user.UserService;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CaptchaService captchaService;

    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public ResponseEntity<?> createNewUser(SignupDTO signupDTO) {
        captchaService.checkCaptcha(signupDTO);
        if (!signupDTO.getPassword1().equals(signupDTO.getPassword2())) {
            return ResponseEntity.badRequest().body(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"));
        }

        /*if (userService.findByFirstName(signupDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"));
        }*/

        User user = userService.createNewUser(signupDTO);
        return ResponseEntity.ok(new UserDTO(user.getId().toString(), user.getFirstName(), user.getEmail()));
    }

    @Override
    public ResponseEntity<?> createAuthToken(LoginDTO loginDTO, HttpServletResponse response) {
        try {
            User user = userService.findByEmail(loginDTO.getEmail());
            if (user == null || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Неправильный логин или пароль");
            }
            Role userRole = roleService.getUserRole();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(userRole.getValue()));

            JwtAuthenticationToken authenticationToken = JwtAuthenticationToken.authenticated(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            TokenDTO tokenDTO = tokenGenerator.createToken(authenticationToken);

            String cookieValue = String.format("jwt=%s; HttpOnly; Secure; Path=/; SameSite=None", tokenDTO.getAccessToken());
            response.setHeader("Set-Cookie", cookieValue);

            return ResponseEntity.ok(tokenDTO);
        } catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка сервера"));
        }
    }

    @Override
    public TokenDTO refreshToken(TokenDTO tokenDTO) {
        Authentication authentication = jwtAuthenticationProvider.authenticate(
                new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));
        Jwt jwt = (Jwt) authentication.getCredentials();

        return tokenGenerator.createToken(authentication);
    }

    @Override
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            SecurityContextHolder.clearContext();

            Cookie cookie = new Cookie("access_token", null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResponseEntity.ok("Выход из системы успешно выполнен");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка выхода из системы"));
        }
    }


}
