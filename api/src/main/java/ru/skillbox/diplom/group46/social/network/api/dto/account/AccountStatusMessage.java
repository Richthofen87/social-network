package ru.skillbox.diplom.group46.social.network.api.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class AccountStatusMessage {
    private UUID userId;
    @JsonProperty("isOnline")
    private boolean isOnline;
    private ZonedDateTime lastLoginTime;

    public AccountStatusMessage(@JsonProperty("userId") UUID userId,
                                @JsonProperty("isOnline") boolean isOnline,
                                @JsonProperty("lastLoginTime") ZonedDateTime lastLoginTime) {
        this.userId = userId;
        this.isOnline = isOnline;
        this.lastLoginTime = lastLoginTime;
    }

}
