package ru.skillbox.diplom.group46.social.network.impl.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.post.LikeReaction;
import java.util.UUID;

@Repository
public interface LikeReactionRepository extends JpaRepository<LikeReaction, UUID> {


}
