-- User Information
CREATE TABLE UserInfo (
    UserID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL
);

-- User Credentials
CREATE TABLE UserCredential (
    CredentialID SERIAL PRIMARY KEY,
    UserID INT UNIQUE NOT NULL,
    UserName VARCHAR(255) UNIQUE NOT NULL,
    HashedPassword TEXT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES UserInfo(UserID)
);
