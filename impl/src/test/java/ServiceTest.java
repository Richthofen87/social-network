
import org.junit.ClassRule;
import org.junit.Test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import ru.skillbox.diplom.group46.social.network.api.dto.auth.UserDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendDto;
import ru.skillbox.diplom.group46.social.network.impl.Application;
import ru.skillbox.diplom.group46.social.network.impl.service.friend.FriendService;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.JwtUserExtractor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("tests")
@ContextConfiguration(initializers = {ServiceTest.Initializer.class})
@DisplayName("I'm a Test Class")
public class ServiceTest  {
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");
    @Autowired
    private FriendService friendService;


    @BeforeEach
    public void setUp() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "user1", "pass", "root@root.com");

        JwtUserExtractor extractor = Mockito.mock(JwtUserExtractor.class);

        Authentication authentication = Mockito.mock(Authentication.class);
        Jwt jwt = Mockito.mock(Jwt.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(jwt);

        Mockito.when(jwt.getTokenValue()).thenReturn("mockTokenValue");

        Mockito.when(extractor.getUserFromToken("mockTokenValue")).thenReturn(userDTO);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        CurrentUserExtractor.staticExtractor = extractor;
    }

    @Test
    @DisplayName("Тестирование добавления в друзья")
    @Transactional
    public void AddFriendTest() {
        setUp();
        FriendDto friendDto = friendService.addFriendRequest(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"));
        System.out.printf(friendDto.toString());
        assertThat(friendDto.getFriendId()).isEqualTo(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"));
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
