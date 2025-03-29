package com.codenbugs.ms_user.models.user_information;

import com.codenbugs.ms_user.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "user_has_information")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserHasInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String photo_path;

    private String name;

    private Integer age;

    private String description;

    private BigDecimal current_balance;

    @OneToOne
    @JoinColumn(name = "FK_User")
    private User user;
}
