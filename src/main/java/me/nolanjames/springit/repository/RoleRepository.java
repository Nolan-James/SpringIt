package me.nolanjames.springit.repository;

import me.nolanjames.springit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
