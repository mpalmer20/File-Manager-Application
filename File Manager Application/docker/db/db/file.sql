-- File Metadata
CREATE TABLE FileMetadata (
    FileID SERIAL PRIMARY KEY,
    OwnerID INT NOT NULL,
    FileName VARCHAR(255) NOT NULL,
    FileSize BIGINT,
    CreationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ModificationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FilePath VARCHAR(1024) NOT NULL, -- Assuming /home/ntu-user/App as base directory
    FOREIGN KEY (OwnerID) REFERENCES UserInfo(UserID)
);

-- Encryption Keys
CREATE TABLE EncryptionKeys (
    FileID INT UNIQUE NOT NULL,
    EncryptionKey TEXT NOT NULL,
    FOREIGN KEY (FileID) REFERENCES FileMetadata(FileID)
);
