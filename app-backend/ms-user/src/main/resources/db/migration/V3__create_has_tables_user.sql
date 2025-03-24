CREATE TABLE IF NOT EXISTS user_has_hobbies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    FK_User INT NOT NULL REFERENCES users(id),
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_has_labels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    FK_User INT NOT NULL REFERENCES users(id),
    FK_Label INT NOT NULL REFERENCES labels(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_has_roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    FK_User INT NOT NULL REFERENCES users(id),
    FK_Role INT NOT NULL REFERENCES roles(id)
) ;

CREATE TABLE IF NOT EXISTS modules (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    path VARCHAR(50) NOT NULL,
    is_enabled TINYINT(1) DEFAULT 1
) ;