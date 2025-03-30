package com.codenbugs.gateway.repositories.ads;

import com.codenbugs.gateway.models.ads.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
