package ru.skillbox.diplom.group46.social.network.impl.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;

import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, UUID> {
    @Override
    void delete(T entity);

    @Override
    void deleteAll();

    @Override
    T getById(UUID uuid);

    void hardDeleteById(UUID uuid);

    void hardDeleteAll();
}
