package com.cloudlabs.library.repository;

import com.cloudlabs.library.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import com.cloudlabs.library.model.Role;

@Repository
@Qualifier("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnum role);
}