package ru.skillbox.diplom.group46.social.network.domain.user.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.domain.user.User;

import java.util.Set;
import java.util.UUID;

/**
 * Role
 *
 * @author vladimir.sazonov
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID uuid;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}