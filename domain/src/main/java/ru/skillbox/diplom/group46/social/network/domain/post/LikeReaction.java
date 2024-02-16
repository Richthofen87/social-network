package ru.skillbox.diplom.group46.social.network.domain.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "like_reaction")
public class LikeReaction{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column (name = "reaction_type")
    private String reactionType;

    @Column (name = "count")
    private Integer count;

    @ManyToOne(cascade = {CascadeType.ALL},fetch= FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;
}
