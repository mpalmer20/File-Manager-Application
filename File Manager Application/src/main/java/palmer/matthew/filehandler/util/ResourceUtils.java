package palmer.matthew.filehandler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceUtils {

  private static final String CONFIG_FILE = "application.properties";
  private static final Properties properties = new Properties();

  static {
    loadProperties();
  }
  public static final String PRIMARY_FXML_PATH = properties.getProperty("fxml.path.primary");
  public static final String REGISTER_USER_FXML_PATH = properties.getProperty("fxml.path.register");
  public static final String FILE_MANAGER_FXML_PATH = properties.getProperty("fxml.path.file");


  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private ResourceUtils() {
    throw new AssertionError(this.getClass().getName() + " instantiation not allowed");
  }



  private static void loadProperties() {
    try (
        InputStream input = ResourceUtils.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
      if (input == null) {
        throw new IOException("Unable to find " + CONFIG_FILE);
      }
      properties.load(input);
    } catch (IOException ex) {
      throw new RuntimeException("Error loading properties file", ex);
    }
  }


}
