package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import protocol.MessageType;
import util.PrintLogUtil;

/**
 * The Handler class for translate the stream into message, which will be presented to user
 */
public class OutputHandler {

  private final static int NO_USER = 0;
  private String userName;
  private final DataInputStream fromServer;


  /**
   * The constructor for the class
   *
   * @param userName   user's name
   * @param fromServer dataInputStream of client
   */
  public OutputHandler(String userName, DataInputStream fromServer) {
    this.userName = userName;
    this.fromServer = fromServer;
  }

  /**
   * translating server's stream information into message and show to the user
   *
   * @param messageType The message type
   * @throws IOException if dataInputStream broken, then it will throw
   */
  public void outPuthandle(MessageType messageType)
      throws IOException {
    switch (messageType) {

      case QUERY_RESPONSE -> {
        List<String> allUser = new ArrayList<>();
        fromServer.readChar();
        int numberOfUsers = fromServer.readInt();
        if (numberOfUsers > NO_USER) {
          for (int i = 0; i < numberOfUsers; i++) {
            fromServer.readChar();
            int nameSize = fromServer.readInt();
            fromServer.readChar();
            byte[] oneUser = new byte[nameSize];
            fromServer.read(oneUser);
            String name = new String(oneUser, StandardCharsets.UTF_8);
            allUser.add(name);
          }
        }
        PrintLogUtil.queryMessage(userName, allUser);
      }

      case DIRECT_MESSAGE -> {
        fromServer.readChar();
        int senderNameSize = fromServer.readInt();
        fromServer.readChar();
        byte[] senderUser = new byte[senderNameSize];
        fromServer.read(senderUser);
        String senderName = new String(senderUser, StandardCharsets.UTF_8);
        fromServer.readChar();
        int receiverNameSize = fromServer.readInt();
        fromServer.readChar();
        byte[] receiverUser = new byte[receiverNameSize];
        fromServer.read(receiverUser);
        String receiverName = new String(receiverUser, StandardCharsets.UTF_8);
        fromServer.readChar();
        int messageSize = fromServer.readInt();
        fromServer.readChar();
        byte[] message = new byte[messageSize];
        fromServer.read(message);
        String backMessage = new String(message, StandardCharsets.UTF_8);
        PrintLogUtil.oneOnOneMessage(senderName, receiverName, backMessage);
      }

      case FAILED_MESSAGE -> {
        fromServer.readChar();
        int messageSize = fromServer.readInt();
        fromServer.readChar();
        byte[] message = new byte[messageSize];
        fromServer.read(message);
        String backMessage = new String(message, StandardCharsets.UTF_8);
        PrintLogUtil.errorMessage(this.userName, backMessage);
      }
    }
  }

  /**
   * handel any connection related response, such as connection response and disconnect response
   *
   * @return if the connection/disconnection is in success status
   * @throws IOException if dataInputStream broken, then it will throw
   */
  public boolean connectStatusResponseHandle() throws IOException {
    fromServer.readChar();
    boolean successStatus = fromServer.readBoolean();
    fromServer.readChar();
    int messageSize = fromServer.readInt();
    fromServer.readChar();
    byte[] message = new byte[messageSize];
    fromServer.read(message);
    String backMessage = new String(message, StandardCharsets.UTF_8);
    if (successStatus) {
      PrintLogUtil.successMessage(userName, backMessage);
    } else {
      PrintLogUtil.errorMessage(userName, backMessage);
    }
    return successStatus;
  }

  /**
   * Get the user's name
   *
   * @return user's name
   */
  public String getUserName() {
    return userName;
  }

  /**
   * set username
   *
   * @param userName the new username
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * check if two objects are equal
   *
   * @param o the other object
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OutputHandler that = (OutputHandler) o;
    return Objects.equals(userName, that.userName);
  }

  /**
   * calculate the hashcode of the object
   *
   * @return the hashcode of object
   */
  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }

  /**
   * to string
   *
   * @return the string representation
   */
  @Override
  public String toString() {
    return "OutputHandler{" +
        "userName='" + userName + '\'' +
        ", fromServer=" + fromServer +
        '}';
  }
}
