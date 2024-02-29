package ru.skillbox.diplom.group46.social.network.domain.user;

import jakarta.persistence.*;
import lombok.*;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import ru.skillbox.diplom.group46.social.network.domain.user.role.Role;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User
 *
 * @author vladimir.sazonov
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="\"user\"")
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "reg_date")
    private ZonedDateTime regDate;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        role.getUsers().add(this);
        roles.add(role);
    }
}