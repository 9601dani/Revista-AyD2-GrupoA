package com.codenbugs.ms_user.models.labels;

import com.codenbugs.ms_user.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_has_labels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserHasLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_User")
    private User user;

    @ManyToOne
    @JoinColumn(name = "FK_Label")
    private Label label;
}
