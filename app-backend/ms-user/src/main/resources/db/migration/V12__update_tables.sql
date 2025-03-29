ALTER TABLE roles_has_pages ADD COLUMN can_created TINYINT(1) DEFAULT 1;
ALTER TABLE roles_has_pages ADD COLUMN can_update TINYINT(1) DEFAULT 1;
ALTER TABLE roles_has_pages ADD COLUMN can_delete TINYINT(1) DEFAULT 1;