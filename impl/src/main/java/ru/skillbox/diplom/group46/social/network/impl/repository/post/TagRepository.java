package ru.skillbox.diplom.group46.social.network.impl.repository.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.domain.tag.Tag;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TagRepository extends BaseRepository<Tag, UUID> {

    @Override
    public default void hardDeleteById(UUID uuid) {
        deleteById(uuid);
    }

    @Override
    public default void hardDeleteAll() {
        deleteAll();
    }

    Tag findByName(String name);

    @Query(value = "SELECT t.id, t.is_deleted, t.name FROM Tag t JOIN post_tag pt ON t.id = pt.tag_id GROUP BY t.id, t.name ORDER BY COUNT(pt.tag_id) DESC", nativeQuery = true)
    List<Tag> findTagsOrderedByPostCountDesc();
}
