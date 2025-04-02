package com.codenbugs.ms_ads.repositories.users;

import com.codenbugs.ms_ads.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
