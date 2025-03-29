package com.codenbugs.ms_user.models.role_has_page;

import com.codenbugs.ms_user.models.page.Page;
import com.codenbugs.ms_user.models.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles_has_pages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleHasPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_Role")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "FK_Page")
    private Page page;

    @Column(name = "can_created", columnDefinition = "TINYINT")
    private Boolean canCreate = true;

    @Column(name = "can_update", columnDefinition = "TINYINT")
    private Boolean canEdit = true;

    @Column(name = "can_delete",columnDefinition = "TINYINT")
    private Boolean canDelete = true;
}
