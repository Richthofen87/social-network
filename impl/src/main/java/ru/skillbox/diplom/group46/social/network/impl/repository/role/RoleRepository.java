package ru.skillbox.diplom.group46.social.network.impl.repository.role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.user.role.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByValue(String name);
}