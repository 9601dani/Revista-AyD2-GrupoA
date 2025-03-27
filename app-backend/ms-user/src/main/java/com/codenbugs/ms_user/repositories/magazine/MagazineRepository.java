package com.codenbugs.ms_user.repositories.magazine;

import com.codenbugs.ms_user.models.magazine.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {
}
