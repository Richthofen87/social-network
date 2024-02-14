package ru.skillbox.diplom.group46.social.network.domain.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID uuid;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}