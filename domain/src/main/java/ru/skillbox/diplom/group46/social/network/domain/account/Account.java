package ru.skillbox.diplom.group46.social.network.domain.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ru.skillbox.diplom.group46.social.network.domain.account.status_code.StatusCode;
import ru.skillbox.diplom.group46.social.network.domain.user.User;

import java.time.ZonedDateTime;

/**
 * Account
 *
 * @author vladimir.sazonov
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Account extends User {

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo")
    private String photo;

    @Column(name = "profile_cover")
    private String profileCover;

    @Column(name = "about")
    private String about;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_code")
    private StatusCode statusCode;

    @Column(name = "birth_date")
    private ZonedDateTime birthDate;

    @Column(name = "message_permission")
    private String messagePermission;

    @Column(name = "last_online_time")
    private ZonedDateTime lastOnlineTime;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @Column(name = "emoji_status")
    private String emojiStatus;

    @Column(name = "deletion_timestamp")
    private ZonedDateTime deletionTimestamp;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;
}