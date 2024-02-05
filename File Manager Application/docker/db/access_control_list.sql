CREATE TABLE file_access_acl (
    acl_id SERIAL PRIMARY KEY,
    file_id INT NOT NULL,
    user_id INT NOT NULL,
    permission_type VARCHAR(5) NOT NULL,
    FOREIGN KEY (file_id) REFERENCES file_metadata(file_id),
    FOREIGN KEY (user_id) REFERENCES user_info(user_id),
    CHECK (permission_type IN ('read', 'write')),
    UNIQUE (file_id, user_id, permission_type)
);
