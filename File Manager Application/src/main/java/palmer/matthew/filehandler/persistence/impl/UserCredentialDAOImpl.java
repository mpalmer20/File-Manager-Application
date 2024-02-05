package palmer.matthew.filehandler.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import palmer.matthew.filehandler.model.UserCredential;
import palmer.matthew.filehandler.persistence.DatabaseConnector;
import palmer.matthew.filehandler.persistence.UserCredentialDAO;

public class UserCredentialDAOImpl implements UserCredentialDAO {

  private static final String INSERT_QUERY =
      "INSERT INTO user_credential (user_id, hashed_password) VALUES (?, ?)";
  private static final String FIND_BY_USERID_QUERY =
      "SELECT * FROM user_credential WHERE user_id = ?";

  @Override
  public void insert(UserCredential userCredential) throws SQLException {
    try (Connection conn = DatabaseConnector.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY)) {
      pstmt.setString(1, userCredential.getUserId());
      String encodedPassword =
          Base64.getEncoder().encodeToString(userCredential.getHashedPassword());
      pstmt.setString(2, encodedPassword);
      pstmt.executeUpdate();
    }
  }

  @Override
  public UserCredential findByUserId(String userId) throws SQLException {
    Connection conn = DatabaseConnector.getConnection();
    try (PreparedStatement pstmt = conn.prepareStatement(FIND_BY_USERID_QUERY)) {
      pstmt.setString(1, userId);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          String userIdRes = rs.getString("user_id");
          String hashedPasswordBase64 = rs.getString("hashed_password");
          // Decode the Base64 string back to byte array
          byte[] hashedPassword = Base64.getDecoder().decode(hashedPasswordBase64);

          return new UserCredential(userIdRes, hashedPassword);
        }
      }
    } catch (Exception e) {
      conn.close();
      conn = null;
      throw e;
    }
    return null;
  }

}
