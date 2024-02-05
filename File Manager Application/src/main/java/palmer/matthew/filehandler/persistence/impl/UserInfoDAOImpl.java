package palmer.matthew.filehandler.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import palmer.matthew.filehandler.model.UserInfo;
import palmer.matthew.filehandler.persistence.DatabaseConnector;
import palmer.matthew.filehandler.persistence.UserInfoDAO;

public class UserInfoDAOImpl implements UserInfoDAO {

  private static final String INSERT_QUERY = "INSERT INTO user_info (full_name, email) VALUES (?, ?)";
  private static final String FIND_BY_NAME_QUERY = "SELECT * FROM user_info WHERE name = ?";

  @Override
  public void insert(UserInfo userInfo) throws SQLException {
    try (Connection conn = DatabaseConnector.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY)) {
      pstmt.setString(1, userInfo.getName());
      pstmt.setString(2, userInfo.getEmail());
      pstmt.executeUpdate();
    } // Try-with-resources ensures that each resource is closed at the end of the statement
  }

  @Override
  public UserInfo findByName(String name) throws SQLException {
    try (Connection conn = DatabaseConnector.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(FIND_BY_NAME_QUERY)) {
      pstmt.setString(1, name);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return new UserInfo(rs.getString("full_name"), rs.getString("email"),
              rs.getString("user_id"));
        }
      }
    }
    return null;
  }

}
