package com.codenbugs.ms_user.repositories.magazine;

import com.codenbugs.ms_user.models.magazine.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> findByPath(String path);

    List<Document> findAllByMagazine_Id(Integer magazineId);


}
