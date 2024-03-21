package ru.skillbox.diplom.group46.social.network.impl.utils.specification;

import jakarta.persistence.metamodel.SingularAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;

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

    public static Specification inListJoin(List<String> tags, String tableName, String columnName) {
        {
            return (root, query, builder) ->
                    tags == null || tags.isEmpty() ? builder.conjunction()
                            : builder.in(root.join(tableName).get(columnName)).value(tags);
        }
    }

    public static Specification ContainsValue(SingularAttribute singularAttribute, String string) {
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
