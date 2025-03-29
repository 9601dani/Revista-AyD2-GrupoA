package com.codenbugs.ms_user.models.page;

import com.codenbugs.ms_user.models.module.Module;
import com.codenbugs.ms_user.models.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String path;

    @ManyToOne
    @JoinColumn(name = "FK_Module")
    private Module module;

    @Column(name = "is_enabled",columnDefinition = "TINYINT")
    private Boolean isEnabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_has_pages",
            joinColumns = @JoinColumn(name = "FK_Page"),
            inverseJoinColumns = @JoinColumn(name = "FK_Role")
    )
    private List<Role> roles = new ArrayList<>();
}
