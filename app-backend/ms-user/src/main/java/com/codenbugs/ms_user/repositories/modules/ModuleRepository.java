package com.codenbugs.ms_user.repositories.modules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.codenbugs.ms_user.models.module.Module;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    @Query(value = """
            SELECT m.* from modules m 
            JOIN pages p ON p.FK_Module = m.id
            JOIN roles_has_pages rhp ON rhp.FK_Page = p.id
            JOIN roles r ON rhp.FK_Role = r.id
            JOIN user_has_roles uhr ON uhr.FK_Role = r.id
            JOIN users u ON u.id = uhr.FK_User
            WHERE u.id=:id AND p.is_enabled = 1 AND m.is_enabled = 1
            GROUP BY m.id
            """, nativeQuery = true)
    List<Module> findModulesByUserId(@Param("id") Integer id);
}
