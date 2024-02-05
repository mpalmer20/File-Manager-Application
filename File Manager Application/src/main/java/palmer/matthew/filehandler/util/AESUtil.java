package palmer.matthew.filehandler.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

  private static final String ALGORITHM = "AES";
  private static final String KEY_FILE_PATH = "src/main/resources/secretKey";
  private static Key SECRET_KEY;

  static {
    try {
      File keyFile = new File(KEY_FILE_PATH);
      if (keyFile.exists()) {
        // Load the existing key
        byte[] keyBytes = Files.readAllBytes(keyFile.toPath());
        SECRET_KEY = new SecretKeySpec(keyBytes, ALGORITHM);
      } else {
        // Generate a new key if file does not exist
        SecretKey secretKey = generateKey();
        SECRET_KEY = secretKey;
        // Save the new key to the file
        try (FileOutputStream fos = new FileOutputStream(keyFile)) {
          fos.write(secretKey.getEncoded());
        }
      }
    } catch (IOException | GeneralSecurityException e) {
      throw new RuntimeException("Failed to initialize AES key", e);
    }
  }

  private AESUtil() {
    throw new AssertionError(this.getClass().getName() + " instantiation not allowed");
  }

  private static SecretKey generateKey() throws GeneralSecurityException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
    keyGenerator.init(256);
    return keyGenerator.generateKey();
  }

  public static byte[] encryptFile(byte[] inputBytes) throws GeneralSecurityException {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
    return cipher.doFinal(inputBytes);
  }

  public static byte[] decryptFile(byte[] inputBytes) throws GeneralSecurityException {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
    return cipher.doFinal(inputBytes);
  }
}
