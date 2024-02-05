package palmer.matthew.filehandler.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Vector;
import palmer.matthew.filehandler.util.AESUtil;

public class FileService {
  private static final String SFTP_HOST = "localhost"; // Adjust if your Docker host differs
  private static final int SFTP_PORT = 2222; // Port mapped to the Docker SFTP server
  private static final String SFTP_USER = "sftpuser";
  private static final String SFTP_PASS = "pass";
  private static final String SFTP_DIRECTORY = "/upload"; // Target directory in the
                                                                        // SFTP server

  private Session session;
  private ChannelSftp sftpChannel;

  public FileService() throws Exception {
    sftpChannel = initializeSFTPChannel();
  }


  private ChannelSftp initializeSFTPChannel() throws Exception {
    JSch jsch = new JSch();
    Session session = jsch.getSession(SFTP_USER, SFTP_HOST, SFTP_PORT);
    session.setConfig("StrictHostKeyChecking", "no");
    session.setPassword(SFTP_PASS);
    session.connect();

    ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
    channel.connect();
    return channel;
  }

  public Vector<ChannelSftp.LsEntry> listFiles() throws Exception {
    return sftpChannel.ls(SFTP_DIRECTORY);
  }

  public void uploadFile(String localFilePath, byte[] fileContent) throws Exception {
    byte[] encryptedContent = AESUtil.encryptFile(fileContent); // Assume AESUtil.encrypt() exists
    InputStream inputStream = new ByteArrayInputStream(encryptedContent);
    sftpChannel.put(inputStream, SFTP_DIRECTORY + "/" + localFilePath);
    inputStream.close();
  }

  public void updateFile(String filePath, byte[] newFileContent) throws Exception {
    // Assuming update is an overwrite operation
    uploadFile(filePath, newFileContent); // Reuse upload logic for update
  }

  public void deleteFile(String filePath) throws Exception {
    sftpChannel.rm(SFTP_DIRECTORY + "/" + filePath);
  }

  public byte[] downloadFile(String filePath) throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    sftpChannel.get(SFTP_DIRECTORY + "/" + filePath, outputStream);
    byte[] encryptedContent = outputStream.toByteArray();
    return AESUtil.decryptFile(encryptedContent); // Assume AESUtil.decrypt() exists
  }

  public void disconnect() {
    if (sftpChannel != null) {
      sftpChannel.disconnect();
    }
    if (session != null) {
      session.disconnect();
    }
  }

  @Override
  protected void finalize() throws Throwable {
    disconnect(); // Ensure resources are freed on garbage collection
    super.finalize();
  }
}
