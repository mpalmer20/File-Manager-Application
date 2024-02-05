-- File Metadata
CREATE TABLE file_metadata (
    file_id SERIAL PRIMARY KEY,
    owner_id INT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_size BIGINT,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    file_path VARCHAR(1024) NOT NULL, -- Assuming /home/ntu-user/App as base directory
    FOREIGN KEY (owner_id) REFERENCES user_info(user_id)
);


-- Encryption Keys
CREATE TABLE encryption_keys (
    file_id INT UNIQUE NOT NULL,
    encryption_key TEXT NOT NULL,
    FOREIGN KEY (file_id) REFERENCES file_metadata(file_id)
);
