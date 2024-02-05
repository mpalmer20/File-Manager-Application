-- Audit Trail for tracking file access and modifications
CREATE TABLE AuditTrail (
    AuditID SERIAL PRIMARY KEY,
    FileID INT NOT NULL,
    UserID INT NOT NULL,
    Action VARCHAR(16) NOT NULL NOT NULL,
    ActionDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (FileID) REFERENCES FileMetadata(FileID),
    FOREIGN KEY (UserID) REFERENCES UserInfo(UserID)
);
