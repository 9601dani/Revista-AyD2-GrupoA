package com.codenbugs.ms_user.repositories.user_has_role;

import com.codenbugs.ms_user.models.user_has_role.UserHasRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHasRoleRepository extends JpaRepository<UserHasRole, Integer> {
}
