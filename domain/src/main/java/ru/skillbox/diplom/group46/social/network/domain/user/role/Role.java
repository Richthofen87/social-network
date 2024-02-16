package ru.skillbox.diplom.group46.social.network.domain.user.role;

import jakarta.persistence.*;
import lombok.*;
import ru.skillbox.diplom.group46.social.network.domain.user.User;

import java.util.Set;
import java.util.UUID;

/**
 * Role
 *
 * @author vladimir.sazonov
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID uuid;

    @Column(name = "value")
    private String value;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
