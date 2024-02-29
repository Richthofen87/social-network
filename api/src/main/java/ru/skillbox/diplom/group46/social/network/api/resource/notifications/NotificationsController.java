package ru.skillbox.diplom.group46.social.network.api.resource.notifications;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Settings;

/**
 * NotificationsController
 *
 * @author vladimir.sazonov
 */
@RestController
@RequestMapping("api/v1/notifications")
public interface NotificationsController {

    @GetMapping("/settings")
    ResponseEntity<Settings> get();

    @PutMapping("/settings")
    ResponseEntity<Boolean> update();
}
