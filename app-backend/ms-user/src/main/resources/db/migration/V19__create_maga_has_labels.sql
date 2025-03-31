CREATE TABLE IF NOT EXISTS magazine_has_labels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    FK_Magazine INT NOT NULL REFERENCES magazines(id),
    FK_Label INT NOT NULL REFERENCES labels(id)
);

CREATE TABLE IF NOT EXISTS magazine_has_categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    FK_Magazine INT NOT NULL REFERENCES magazines(id),
    FK_Category INT NOT NULL REFERENCES categories(id)
);