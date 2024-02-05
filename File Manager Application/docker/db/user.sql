-- User Information
CREATE TABLE user_info (
    user_id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- User Credentials
CREATE TABLE user_credential (
    user_id VARCHAR(255) PRIMARY KEY,
    hashed_password TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_info(user_id)
);
