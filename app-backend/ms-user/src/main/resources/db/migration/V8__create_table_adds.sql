CREATE TABLE IF NOT EXISTS adds(
    id INT AUTO_INCREMENT PRIMARY KEY,
    FK_Period INT NOT NULL REFERENCES periods(id),
    FK_Adds_Types INT NOT NULL REFERENCES adds_types(id),
    FK_User INT NOT NULL REFERENCES users(id),
    content VARCHAR(255),
    path VARCHAR(100),
    is_enabled TINYINT(1) DEFAULT 1,
    number_views INT DEFAULT 0,
    date_created DATE NOT NULL,
    date_ended DATE NOT NULL
);
