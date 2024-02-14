package ru.skillbox.diplom.group46.social.network.impl.repository.base;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;

import java.util.UUID;

@NoRepositoryBean
public class BaseRepositoryImpl<T extends BaseEntity> extends SimpleJpaRepository<T, UUID> implements BaseRepository<T> {

    public BaseRepositoryImpl(JpaEntityInformation<T, UUID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public void hardDeleteById(UUID uuid) {
        super.deleteById(uuid);
    }

    @Override
    public void hardDeleteAll() {
        super.deleteAll();
    }

    @Override
    public void delete(T entity) {
        entity.setIsDeleted(true);
        super.save(entity);
    }

    @Override
    public void deleteAll() {
        super.findAll().stream()
                .peek(entity -> entity.setIsDeleted(true))
                .forEach(super::save);
    }

    @Override
    public T getById(UUID uuid) {
        return super.findById(uuid).get();
    }
}