package com.codenbugs.ms_user.repositories.label;

import com.codenbugs.ms_user.models.labels.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {

    public Optional<Label> findLabelByName(@Param("name") String name);

    Label findByName(String name);
}
