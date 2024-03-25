package ru.skillbox.diplom.group46.social.network.impl.service.notifications;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "notifications-service",url = "http://${notificationsApi.url}/api/v1/notifications")
public interface NotificationsClient {

    @PutMapping("/softDeleteFriendRequests")
    Boolean softDeleteFriendRequests(@RequestParam UUID authorId, @RequestParam UUID friendId);
}
