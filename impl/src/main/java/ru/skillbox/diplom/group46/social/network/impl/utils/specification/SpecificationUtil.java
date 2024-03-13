package ru.skillbox.diplom.group46.social.network.impl.utils.specification;

import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostDto;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import ru.skillbox.diplom.group46.social.network.domain.friend.Friend;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.domain.tag.Tag;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class SpecificationUtil<T> {

    public static Specification equalValue(SingularAttribute singularAttribute, Object object) {
        return (root, query, builder) -> object == null ? null : builder.equal(root.get(singularAttribute), object);
    }
    public static Specification notEqualValue(SingularAttribute singularAttribute, Object object) {
        return (root, query, builder) -> object == null ? null : builder.notEqual(root.get(singularAttribute), object);
    }

    public static Specification equalValueUUID(SingularAttribute attribute, UUID value) {
        return (root, query, builder) -> value == null ? null : builder.equal(root.get(attribute), value);
    }

    public static Specification<PostDto> withFriends(Boolean withFriends, UUID currentUser) {
        return (root, query, cb) -> {
            if (withFriends != null && withFriends) {
                Subquery<UUID> friendSubquery = query.subquery(UUID.class);
                Root<Friend> friendRoot = friendSubquery.from(Friend.class);
                friendSubquery.select(friendRoot.get("friendId"));
                friendSubquery.where(
                        cb.equal(friendRoot.get("authorId"), currentUser),
                        cb.equal(friendRoot.get("isDeleted"), false),
                        cb.equal(friendRoot.get("statusCode"), "FRIEND")
                );

                return root.get("authorId").in(friendSubquery);
            } else {
                return null;
            }
        };
    }



    public static Specification<Post> IsContainsTags(List<String> tags) {

        return (root, query, builder) -> {
            if (tags == null || tags.isEmpty()) {
                return builder.conjunction();
            } else {
                Join<Post, Tag> tagJoin  = root.join("tags", JoinType.INNER);
                Expression<String> tagNameExpression = tagJoin.get("name");
                return tagNameExpression.in(tags);
            }
        };
    }

    public static Specification isContainsAuthor(SingularAttribute<Post, UUID> id, List<UUID> ids) {
        return (root, query, builder) -> ids == null || ids.isEmpty() ? builder.conjunction() :
                builder.in(root.get(id)).value(ids);
    }

    public static Specification isContainsValue(SingularAttribute singularAttribute, String string) {
        return (root, query, builder) -> string == null ? builder.conjunction() :
                builder.like(root.get(singularAttribute), "%" + string + "%");
    }

    public static Specification isLessValue(SingularAttribute singularAttribute, Comparable comparable) {
        return (root, query, builder) -> comparable == null || singularAttribute == null ? builder.conjunction() :
                builder.lessThan(root.get(singularAttribute), comparable);
    }

    public static Specification isGreatValue(SingularAttribute singularAttribute, Comparable comparable) {
        return (root, query, builder) -> comparable == null || singularAttribute == null ? builder.conjunction() :
                builder.greaterThan(root.get(singularAttribute), comparable);
    }

    public static Specification isBetween(SingularAttribute singularAttribute, Comparable comparable, Comparable comparableTwo) {
        return (root, query, builder) -> comparable == null && comparableTwo == null ? builder.conjunction() :
                builder.between(root.get(singularAttribute), comparable, comparableTwo);
    }

    public static Specification equalValueUUIDList(SingularAttribute<BaseEntity, UUID> id, List<UUID> ids) {
        return (root, query, builder) -> ids == null || ids.isEmpty() ? builder.conjunction() :
                builder.in(root.get(id)).value(ids);
    }
}
