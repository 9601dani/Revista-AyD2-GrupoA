package com.codenbugs.ms_user.repositories.magazine;

import com.codenbugs.ms_user.models.magazine.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}
