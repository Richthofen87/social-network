package ru.skillbox.diplom.group46.social.network.impl.repository.geo;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.geo.City;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CityRepository extends BaseRepository <City, UUID> {
    @Override
    public default void hardDeleteById(UUID uuid) {
        deleteById(uuid);
    }

    @Override
    public default void hardDeleteAll() {
        deleteAll();
    }

    List<City> findAllById(UUID countryId);
}
