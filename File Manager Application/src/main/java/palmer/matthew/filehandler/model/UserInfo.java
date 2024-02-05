/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package palmer.matthew.filehandler.model;

/**
 * @author Sayantan
 *
 */
public class UserInfo {
  private String userId;
  private String name;
  private String email;
  // @NOTE : Add additional user data as required.

  // Constructor
  public UserInfo(String userId, String name, String email) {
    this.userId = userId;
    this.name = name;
    this.email = email;
  }

  // Getters and Setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
