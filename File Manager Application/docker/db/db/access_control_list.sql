-- Access Control List (ACL)
CREATE TABLE FileAccessACL (
    ACLID SERIAL PRIMARY KEY,
    FileID INT NOT NULL,
    UserID INT NOT NULL,
    PermissionType VARCHAR(10) NOT NULL NOT NULL,
    FOREIGN KEY (FileID) REFERENCES FileMetadata(FileID),
    FOREIGN KEY (UserID) REFERENCES UserInfo(UserID),
    UNIQUE (FileID, UserID, PermissionType)
);
