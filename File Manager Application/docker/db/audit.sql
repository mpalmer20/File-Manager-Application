-- Audit Trail for tracking file access and modifications
CREATE TABLE audit_trail (
    audit_id SERIAL PRIMARY KEY,
    file_id INT NOT NULL,
    user_id INT NOT NULL,
    action VARCHAR(6) NOT NULL,
    action_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (file_id) REFERENCES file_metadata(file_id),
    FOREIGN KEY (user_id) REFERENCES user_info(user_id),
    CHECK (action IN ('create', 'update', 'delete', 'read'))
);
