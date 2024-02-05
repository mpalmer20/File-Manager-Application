package palmer.matthew.filehandler.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DatabaseConnector provides connection to the SQLite database. It reads database configurations
 * from application.properties.
 */
public class DatabaseConnector {
  private static final String SQLITE_JDBC_DRIVER_CLASS = "org.sqlite.JDBC";
  private static Connection connection = null;
  private static final Logger LOGGER = Logger.getLogger(DatabaseConnector.class.getName());
  private static final String CONFIG_FILE = "application.properties";
  private static final Properties properties = new Properties();

  static {
    loadProperties();
  }

  private static final String DATABASE_URL = properties.getProperty("database.url");

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private DatabaseConnector() {
    throw new AssertionError(this.getClass().getName() + " instantiation not allowed");
  }


  /**
   * Connects to the SQLite database using configurations from application.properties.
   * 
   * @return Connection to the SQLite database.
   * @throws SQLException
   */
  public static Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {

        // Load the SQLite JDBC driver
        Class.forName(SQLITE_JDBC_DRIVER_CLASS);

        // Establish the database connection
        connection = DriverManager.getConnection(DATABASE_URL);

        LOGGER.log(Level.INFO, "Connection to SQLite has been established.");
      } else {
        return connection;
      }
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Connection to SQLite failed: {}", e.getMessage());
    }
    return connection;
  }


  private static void loadProperties() {
    try (InputStream input =
        DatabaseConnector.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
      if (input == null) {
        throw new IOException("Unable to find " + CONFIG_FILE);
      }
      properties.load(input);
    } catch (IOException ex) {
      throw new RuntimeException("Error loading properties file", ex);
    }
  }
}
