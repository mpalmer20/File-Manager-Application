package palmer.matthew.filehandler.persistence;

import java.sql.SQLException;
import palmer.matthew.filehandler.model.UserInfo;

public interface UserInfoDAO {
  void insert(UserInfo userInfo) throws SQLException;

  UserInfo findByName(String name) throws SQLException;
  
}
