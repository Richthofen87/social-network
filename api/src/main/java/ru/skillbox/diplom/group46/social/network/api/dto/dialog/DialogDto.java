package ru.skillbox.diplom.group46.social.network.api.dto.dialog;

import lombok.Data;

@Data
public class DialogDto {
   private String type;
   private String recipientId;
   private MessageDto data;
}
