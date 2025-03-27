package com.codenbugs.ms_user.models.user_has_role;

import com.codenbugs.ms_user.models.role.Role;
import com.codenbugs.ms_user.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_has_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserHasRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_User")

    private User user;

    @ManyToOne
    @JoinColumn(name = "FK_Role")
    private Role role;
}
