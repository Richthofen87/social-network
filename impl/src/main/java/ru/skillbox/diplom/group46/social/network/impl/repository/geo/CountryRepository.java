package ru.skillbox.diplom.group46.social.network.impl.repository.geo;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.geo.Country;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.UUID;


@Repository
public interface CountryRepository extends BaseRepository <Country, UUID> {
}
