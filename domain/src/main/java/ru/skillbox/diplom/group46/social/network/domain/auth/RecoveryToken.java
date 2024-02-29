package ru.skillbox.diplom.group46.social.network.domain.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.domain.user.User;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class RecoveryToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

}
