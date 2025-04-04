package com.codenbugs.ms_ads.repositories.categories;

import com.codenbugs.ms_ads.models.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);
}
