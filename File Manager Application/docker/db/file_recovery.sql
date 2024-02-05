-- Recovery Table to track deletions for recovery within 31 days
CREATE TABLE file_recovery (
    recovery_id SERIAL PRIMARY KEY,
    file_id INT NOT NULL,
    deletion_date TIMESTAMP NOT NULL,
    recovery_path VARCHAR(1024) NOT NULL, -- Path where the file backup is stored
    FOREIGN KEY (file_id) REFERENCES file_metadata(file_id)
);
