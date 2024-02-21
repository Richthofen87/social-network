package ru.skillbox.diplom.group46.social.network.domain.geo;

import jakarta.persistence.*;
import lombok.*;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "city")
public class City extends BaseEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "country_id")
    private UUID countryId;
}
