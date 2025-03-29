CREATE TABLE IF NOT EXISTS suscription(
    id INT AUTO_INCREMENT PRIMARY KEY,
    is_like TINYINT(1) DEFAULT 0,
    FK_User INT NOT NULL REFERENCES users(id),
    FK_Magazine INT NOT NULL REFERENCES magazines(id),
    date_created DATE NOT NULL,
    date_ended DATE NOT NULL,
    is_enabled TINYINT(1) DEFAULT 1,
    pay DECIMAL(10,2) NOT NULL
);