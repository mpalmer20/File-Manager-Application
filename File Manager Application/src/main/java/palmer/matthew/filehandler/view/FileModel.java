package palmer.matthew.filehandler.view;

import javafx.beans.property.SimpleStringProperty;

public class FileModel {
  private final SimpleStringProperty fileName;
  private final SimpleStringProperty fileSize;
  private final SimpleStringProperty lastModified;

  public FileModel(String fileName, Double fileSize, String lastModified) {
    this.fileName = new SimpleStringProperty(fileName);
    if (fileSize > 1e6)
      this.fileSize = new SimpleStringProperty(fileSize.toString() + " GB");
    else if (fileSize > 1e3)
      this.fileSize = new SimpleStringProperty(fileSize.toString() + " MB");
    else
      this.fileSize = new SimpleStringProperty(fileSize.toString() + " KB");
    this.lastModified = new SimpleStringProperty(lastModified);
  }

  // Getters
  public String getFileName() {
    return fileName.get();
  }

  public String getFileSize() {
    return fileSize.get();
  }

  public String getLastModified() {
    return lastModified.get();
  }

  // Property getters for table binding
  public SimpleStringProperty fileNameProperty() {
    return fileName;
  }

  public SimpleStringProperty fileSizeProperty() {
    return fileSize;
  }

  public SimpleStringProperty lastModifiedProperty() {
    return lastModified;
  }
}
