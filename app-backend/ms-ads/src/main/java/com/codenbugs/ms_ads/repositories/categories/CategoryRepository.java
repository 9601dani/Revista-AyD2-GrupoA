package com.codenbugs.ms_ads.repositories.categories;

import com.codenbugs.ms_ads.models.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
