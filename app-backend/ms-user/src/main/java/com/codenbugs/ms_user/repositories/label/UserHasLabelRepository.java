package com.codenbugs.ms_user.repositories.label;

import com.codenbugs.ms_user.dtos.label.LabelResponseDto;
import com.codenbugs.ms_user.models.labels.Label;
import com.codenbugs.ms_user.models.labels.UserHasLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHasLabelRepository extends JpaRepository<UserHasLabel, Integer> {


    @Query(value = """
                select  l.id, l.name from user_has_labels uhl
                    left join labels l on uhl.FK_Label = l.id
                    where uhl.FK_User = :id
            """, nativeQuery = true)
    public List<LabelResponseDto> findLabelsByUserId(@Param("id") Integer id);

    public void deleteLabelsByUserId(@Param("FK_User") Integer id);
}
