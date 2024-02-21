package ru.skillbox.diplom.group46.social.network.domain.tag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {

    @Column (name = "name")
    private String name;

    @ManyToMany
    private List<Post> posts = new ArrayList<>();
}

