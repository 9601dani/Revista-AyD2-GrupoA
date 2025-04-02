package com.codenbugs.gateway.repositories.user;

import com.codenbugs.gateway.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsernameAndEmailAndAuthToken(String username, String email, String authToken);
}
