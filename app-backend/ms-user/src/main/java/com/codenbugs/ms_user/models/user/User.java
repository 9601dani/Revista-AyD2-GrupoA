package com.codenbugs.ms_user.models.user;

import com.codenbugs.ms_user.models.labels.Label;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String authToken;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_has_labels",
            joinColumns = @JoinColumn(name = "FK_User"),
            inverseJoinColumns = @JoinColumn(name = "FK_Label")
    )
    private List<Label> labels = new ArrayList<>();
}
