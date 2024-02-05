# Distributed File Manager

## Overview

This documentation outlines the functionalities of a system designed for secure file management, including user authentication, file encryption, and distributed storage. The system integrates a SQLite database for managing user profiles, file metadata, access control lists (ACLs), encryption keys, and more.

## Functionalities

1. **User Management**
   - Features: create, delete, update, login, logout functionalities.
2. **Database Integration**
   - Stores user profiles, file metadata, ACLs, and encryption keys.
3. **File Management**
   - Capabilities: create, update, delete files. Advanced features include file chunking for versions 2.1 and above, and storage requirements for 2.2 and above.
4. **Encryption/Decryption**
   - Utilizes AES for secure file handling.
5. **File Storage**
   - Various storage options ranging from a single container to multiple encrypted chunks across containers.
6. **Emulation of a Terminal**
   - Supports basic file and directory commands.
7. **Remote Terminal Emulation**
   - Remote access via JSch library.
8. **Permission Management**
   - Fine-grained access control for file sharing
9. **File Chunking**
   - Mechanism for splitting large files
10. **Distributed Docker Containers**
    - Utilizes Docker for scalable file storage.
11. **Audit Trail**
    - Logs access and modification activities.
12. **Access Control**
    - Implements fine-grained access control.
13. **Recovery**
    - Robust system for data recovery.
14. **File Metadata**
    - Maintains essential metadata for each file.
15. **Scalability**
    - Designed for growth with Docker Compose.

___

## Tables Included

- `AuditTrail`
- `FileAccessACL`
- `FileRecovery`
- `UserInfo`
- `EncryptionKeys`
- `FileMetadata`
- `UserCredential`

___

## <u>Setup</u>

### Database Setup

The following steps setup the SQLite database using a docker container and create the schema. The database file is backed up at comp20081.db

> ###### This container does not have a sqlite server, rather setup sqlite environment 
>
> ###### and create the necessary schema for the app.

1. **Go to the docker directory**

   ```
   cd docker
   ```

2. **Build Docker Image**

   ```
   docker build --no-cache -t sqlite .
   ```

3. **Launch Services with Docker Compose**

   ```
   docker-compose up
   ```

#### Initializing database

> `init_db.sh` Script
>
> - This script initializes the SQLite database and executes SQL files to create necessary tables.
> - It first checks for the existence of the database file and creates it if not present.
> - It then executes each `.sql` file in the specified directory to set up the database schema.

If tables are not created upon initial setup, execute `init_db.sh` manually:

```
docker exec -it comp20081-sqlite "/db/scripts/init_db.sh"
```

This ensures that the database is correctly initialized with all required tables for the system's functionality.



### File Server Setup



___

## <u>Execution</u>

From the CLI execute :

```
mvn clean javafx:run
```

