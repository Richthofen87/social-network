package ru.skillbox.diplom.group46.social.network.impl.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;

import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, UUID> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
    void delete(T entity);

    void deleteAll();

    T getById(UUID uuid);

    void hardDeleteById(UUID uuid);

    void hardDeleteAll();
}
