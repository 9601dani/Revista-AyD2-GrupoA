package com.codenbugs.ms_user.repositories.label;

import com.codenbugs.ms_user.models.labels.Label;
import com.codenbugs.ms_user.models.labels.UserHasLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHasLabelRepository extends JpaRepository<UserHasLabel, Integer> {

    public List<UserHasLabel> findLabelsByUserId(@Param("FK_User") Integer id);

    public void deleteLabelsByUserId(@Param("FK_User") Integer id);
}
