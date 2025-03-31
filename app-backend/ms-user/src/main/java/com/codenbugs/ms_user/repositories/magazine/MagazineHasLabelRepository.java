package com.codenbugs.ms_user.repositories.magazine;

import com.codenbugs.ms_user.models.magazine.MagazineHasLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineHasLabelRepository extends JpaRepository<MagazineHasLabel, Integer> {
}
