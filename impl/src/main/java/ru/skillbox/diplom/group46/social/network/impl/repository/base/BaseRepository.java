package ru.skillbox.diplom.group46.social.network.impl.repository.base;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;

import java.util.UUID;
import java.util.stream.Collectors;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
    @Override
    void delete(T entity);

    @Override
    void deleteAll();

    @Override
    T getById(UUID uuid);

    void hardDeleteById(UUID uuid);

    void hardDeleteAll();

}
