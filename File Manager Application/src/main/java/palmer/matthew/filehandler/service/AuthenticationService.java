package palmer.matthew.filehandler.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import palmer.matthew.filehandler.model.UserCredential;
import palmer.matthew.filehandler.model.UserInfo;
import palmer.matthew.filehandler.persistence.UserCredentialDAO;
import palmer.matthew.filehandler.persistence.UserInfoDAO;

public class AuthenticationService {

  private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());

  private UserCredentialDAO userCredentialDAO;
  private UserInfoDAO userInfoDAO;

  public AuthenticationService(UserCredentialDAO userCredentialDAO, UserInfoDAO userInfoDAO) {
    this.userCredentialDAO = userCredentialDAO;
    this.userInfoDAO = userInfoDAO;
  }

  public boolean registerUser(String userId, String userName, String email, String password) {
    try {
      UserCredential userCredential = userCredentialDAO.findByUserId(userId);
      if (null != userCredential)
        throw new RuntimeException(
            "User with Id  '" + userId + "' already exists. Try different username");
      userCredentialDAO.insert(new UserCredential(userId, password));
      userInfoDAO.insert(new UserInfo(userId, userName, email));
      return true;
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "User could not be registered {}", e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean loginUser(String userName, String inputPassword) {
    try {
      UserCredential userCredential = userCredentialDAO.findByUserId(userName);
      if (userCredential != null) {
        return userCredential.verifyPassword(inputPassword);
      }
      return false;
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "User could not be logged in {}", e.getMessage());
      return false;
    }
  }

}
