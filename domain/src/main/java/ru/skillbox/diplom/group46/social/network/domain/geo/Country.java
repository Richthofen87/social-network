package ru.skillbox.diplom.group46.social.network.domain.geo;

import  jakarta.persistence.Column;
import lombok.*;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "country")
public class Country extends BaseEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "city")
    @OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<City> cities;
}
