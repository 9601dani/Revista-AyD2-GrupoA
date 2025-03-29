package com.codenbugs.ms_user.repositories.role_has_page;

import com.codenbugs.ms_user.models.role_has_page.RoleHasPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleHasPageRepository extends JpaRepository<RoleHasPage, Integer> {

    @Query(value = """
            SELECT rhp.* from modules m 
            JOIN pages p ON p.FK_Module = m.id
            JOIN roles_has_pages rhp ON rhp.FK_Page = p.id
            JOIN roles r ON rhp.FK_Role = r.id
            JOIN user_has_roles uhr ON uhr.FK_Role = r.id
            JOIN users u ON u.id = uhr.FK_User
            WHERE u.id=:id AND p.is_enabled = 1 AND m.is_enabled = 1
            GROUP BY rhp.id
            """, nativeQuery = true)
    List<RoleHasPage> findRolePageByUserId(@Param("id") Integer id);

}
