package ru.skillbox.diplom.group46.social.network.api.resource.notifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.*;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Notification;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Settings;

/**
 * NotificationsController
 *
 * @author vladimir.sazonov
 */

@RestController
@RequestMapping("api/v1/notifications")
public interface NotificationsController {

    @GetMapping
    ResponseEntity<Page<ContentDto>> get();

    @GetMapping("/settings")
    ResponseEntity<Settings> getSettings();

    @PutMapping("/settings")
    ResponseEntity<Boolean> updateSettings(@RequestBody SettingUpdateDTO dto);

    @PostMapping("/settings/{id}")
    ResponseEntity<Boolean> setSettings(@PathVariable String id);

    @PutMapping("/readed")
    ResponseEntity<Boolean> setReaded();

    @PostMapping("/test")
    ResponseEntity<Notification> create(@RequestBody Notification note);

    @PostMapping("/add")
    ResponseEntity<Notification> addToDb(@RequestBody Notification note);

    @GetMapping("/page")
    ResponseEntity<Page<ContentDto>> getPage(Pageable pageable);

    @GetMapping("/count")
    ResponseEntity<CountDto> getCount();
}