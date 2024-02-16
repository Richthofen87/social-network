package ru.skillbox.diplom.group46.social.network.impl.service.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.domain.user.role.Role;
import ru.skillbox.diplom.group46.social.network.impl.repository.role.RoleRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        log.debug("Method getUserRole() started");
        return roleRepository.findByValue("user");
    }
}