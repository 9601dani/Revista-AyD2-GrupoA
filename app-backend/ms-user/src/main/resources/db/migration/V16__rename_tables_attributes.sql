RENAME TABLE adds_types TO ad_types;

ALTER TABLE adds DROP FOREIGN KEY adds_ibfk_2;

ALTER TABLE adds CHANGE FK_Adds_Types FK_Ad_Types INT NOT NULL;

RENAME TABLE adds TO ads;

-- 4. Crear claves foráneas con nombres claros
ALTER TABLE ads ADD CONSTRAINT FOREIGN KEY (FK_Ad_Types) REFERENCES ad_types(id);

ALTER TABLE adds_has_labels DROP FOREIGN KEY adds_has_labels_ibfk_1;
ALTER TABLE adds_has_labels DROP FOREIGN KEY adds_has_labels_ibfk_2;

ALTER TABLE adds_has_labels CHANGE FK_Add FK_Ad INT NOT NULL;

RENAME TABLE adds_has_labels TO ad_has_labels;

ALTER TABLE ad_has_labels ADD CONSTRAINT FOREIGN KEY (FK_Ad) REFERENCES ads(id);

ALTER TABLE adds_has_categories DROP FOREIGN KEY adds_has_categories_ibfk_1;

ALTER TABLE adds_has_categories CHANGE FK_Add FK_Ad INT NOT NULL;

RENAME TABLE adds_has_categories TO ad_has_categories;

ALTER TABLE ad_has_categories ADD CONSTRAINT FOREIGN KEY (FK_Ad) REFERENCES ads(id);