-- Recovery Table to track deletions for recovery within 31 days
CREATE TABLE FileRecovery (
    RecoveryID SERIAL PRIMARY KEY,
    FileID INT NOT NULL,
    DeletionDate TIMESTAMP NOT NULL,
    RecoveryPath VARCHAR(1024) NOT NULL, -- Path where the file backup is stored
    FOREIGN KEY (FileID) REFERENCES FileMetadata(FileID)
);
