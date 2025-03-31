package com.codenbugs.ms_user.repositories.magazine;

import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    List<Magazine> findByUserId(Integer userId);
}
