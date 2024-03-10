package ru.skillbox.diplom.group46.social.network.domain.base.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.io.Serializable;
@Getter
@AllArgsConstructor
public class UserJsonType implements Serializable {
    private String uuid;
    private String email;

    public UserJsonType() {
        this.uuid = "";
        this.email = "";

    }
}
