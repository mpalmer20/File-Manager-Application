package palmer.matthew.filehandler.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserCredential {
  private static final int ITERATIONS = 65536;
  private static final int KEY_LENGTH = 512;
  private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
  private static final String SALT_FILE_PATH = "src/main/resources/salt";
  private static final String EXTERNAL_SALT_PATH =
      System.getProperty("user.home") + File.separator + "secretSalt"; // External, writable


  private String userId;
  private byte[] hashedPassword;


  static {
    // Try loading the salt; if it fails, generate a new one.
    byte[] salt = loadSalt();
    if (salt == null) {
      salt = generateSalt();
      saveSalt(salt);
    }
  }


  /**
   * Constructor to be used when creating the DTO from the User input in the UI
   * 
   * @param userName
   * @param password
   */
  public UserCredential(String userId, String password) {
    this.userId = userId;
    this.hashedPassword = hashPassword(password, loadSalt());
  }

  /**
   * Constructor to be used when creating the DTO from database
   * 
   * @param userName
   * @param password
   */
  public UserCredential(String userId, byte[] hashedPassword) {
    this.userId = userId;
    this.hashedPassword = hashedPassword;
  }



  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * Method to verify password
   * 
   * @param inputPassword
   * @return true if password is correct, false otherwise
   */
  public boolean verifyPassword(String inputPassword) {
    byte[] hashedInputPassword = hashPassword(inputPassword, loadSalt());
    Arrays.fill(inputPassword.toCharArray(), Character.MIN_VALUE);
    return Arrays.equals(hashedPassword, hashedInputPassword);
  }

  public byte[] getHashedPassword() {
    return hashedPassword;
  }


  /**
   * Secure random salt generation
   * 
   * @return salt
   */
  private static byte[] generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  private static void saveSalt(byte[] salt) {
    try (FileOutputStream fos = new FileOutputStream(EXTERNAL_SALT_PATH)) {
      fos.write(salt);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private static byte[] loadSalt() {
    try {
      File saltFile = new File(EXTERNAL_SALT_PATH);
      if (saltFile.exists()) {
        return Files.readAllBytes(saltFile.toPath());
      } else {
        // Try loading from resources as a fallback
        try (InputStream is = UserCredential.class.getResourceAsStream("/salt")) {
          if (is != null) {
            return is.readAllBytes();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Hashing password with salt <br/>
   * No direct getter for hashedPassword and salt for security reasons
   * 
   * @param password
   * @param salt
   * @return hashed password
   */
  private byte[] hashPassword(String password, byte[] salt) {
    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
    Arrays.fill(password.toCharArray(), Character.MIN_VALUE);
    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
      return skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
    } finally {
      spec.clearPassword();
    }
  }

}
