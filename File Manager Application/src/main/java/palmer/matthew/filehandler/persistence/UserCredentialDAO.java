package palmer.matthew.filehandler.persistence;

import java.sql.SQLException;
import palmer.matthew.filehandler.model.UserCredential;

public interface UserCredentialDAO {
  void insert(UserCredential userCredential) throws SQLException;

  UserCredential findByUserId(String userId) throws SQLException;
  // Additional methods like update, delete can be added here.
}


