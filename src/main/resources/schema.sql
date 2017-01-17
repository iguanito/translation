CREATE TABLE catalog_business(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  domain VARCHAR(255),
  description VARCHAR(255),
  value_proposition VARCHAR(255)
);

CREATE TABLE catalog_business_translation(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    catalog_business_id INT,
    language_code VARCHAR(255),
    description VARCHAR(255),
    value_proposition VARCHAR(255),
    is_default BOOLEAN
);

ALTER TABLE catalog_business_translation ADD FOREIGN KEY ( catalog_business_id ) REFERENCES catalog_business( id ) ;