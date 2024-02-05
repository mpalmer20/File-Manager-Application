package palmer.matthew.filehandler.service;

import java.time.LocalDateTime;

public class SessionManager {
  private static SessionManager instance = new SessionManager();
  private boolean loggedIn = false;
  private String loggedUserId;
  private LocalDateTime loggedInAt;

  private SessionManager() {}

  public static SessionManager getInstance() {
    return instance;
  }

  public void login(String userId) {
    this.loggedIn = true;
    this.loggedUserId = userId;
    this.loggedInAt = LocalDateTime.now();
  }

  public void logout() {
    this.loggedIn = false;
    this.loggedUserId = null;
    this.loggedInAt = null;
  }

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public String getLoggedInUserId() {
    return this.loggedUserId;
  }

  public LocalDateTime getLoggedInAt() {
    return loggedInAt;
  }

}
