package com.codenbugs.ms_user.dtos.response;

import com.codenbugs.ms_user.enums.MagazineType;
import com.codenbugs.ms_user.models.labels.Label;
import com.codenbugs.ms_user.models.magazine.Category;
import com.codenbugs.ms_user.models.magazine.Document;
import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.models.user.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AllMagazineResponseTest {

    private Magazine baseMagazine() {
        Magazine magazine = new Magazine();
        magazine.setId(1);
        magazine.setName("Test Magazine");
        magazine.setDescription("Desc");
        magazine.setCanComment(true);
        magazine.setCanLike(true);
        magazine.setCanSubscribe(true);
        magazine.setType(MagazineType.FREE);
        magazine.setPrice(BigDecimal.ZERO);
        magazine.setEnabled(true);
        magazine.setDateCreated(LocalDateTime.now());
        User user = new User();
        user.setId(100);
        magazine.setUser(user);
        return magazine;
    }

    @Test
    void constructorShouldMapDocumentsWhenNotNull() {
        Magazine magazine = baseMagazine();
        Document doc = new Document();
        doc.setId(10);
        doc.setPath("path/doc.pdf");
        magazine.setDocuments(List.of(doc));

        AllMagazineResponse response = new AllMagazineResponse(magazine);
        assertNotNull(response.documents());
        assertEquals(1, response.documents().size());
        assertEquals("path/doc.pdf", response.documents().get(0).path());
    }

    @Test
    void constructorShouldMapLabelsWhenNotNull() {
        Magazine magazine = baseMagazine();
        Label label = new Label();
        label.setId(20);
        label.setName("Tech");
        magazine.setLabels(List.of(label));

        AllMagazineResponse response = new AllMagazineResponse(magazine);
        assertNotNull(response.labels());
        assertEquals(1, response.labels().size());
        assertEquals("Tech", response.labels().get(0).name());
    }

    @Test
    void constructorShouldMapCategoriesWhenNotNull() {
        Magazine magazine = baseMagazine();
        Category category = new Category();
        category.setId(30);
        category.setName("Science");
        magazine.setCategories(List.of(category));

        AllMagazineResponse response = new AllMagazineResponse(magazine);
        assertNotNull(response.categories());
        assertEquals(1, response.categories().size());
        assertEquals("Science", response.categories().get(0).name());
    }
}
