package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import protocol.MessageType;
import util.PrintLogUtil;

public class OutputHandler {
  private String userName;
  private DataInputStream fromServer;


  public OutputHandler(String userName, DataInputStream fromServer) {
    this.userName = userName;
    this.fromServer = fromServer;
  }

  public  void outPuthandle(MessageType messageType)
      throws IOException {
    switch(messageType){
      case QUERY_RESPONSE -> {
        List<String> allUser = new ArrayList<>();
        fromServer.readChar();
        int numberOfUsers = fromServer.readInt();
        if(numberOfUsers>0){
          for( int i =0 ; i<numberOfUsers;i++){
            fromServer.readChar();
            int nameSize = fromServer.readInt();
            fromServer.readChar();
            byte[] oneUser = new byte[nameSize];
            fromServer.read(oneUser);
            String name = new String(oneUser, StandardCharsets.UTF_8);
            allUser.add(name);
          }
        }
        PrintLogUtil.queryMessage(userName,allUser);

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
        PrintLogUtil.oneOnOneMessage(senderName,receiverName,backMessage);
      }
      case BROADCAST_MESSAGE -> {
        fromServer.readChar();
        int senderNameSize = fromServer.readInt();
        fromServer.readChar();
        byte[] senderUser = new byte[senderNameSize];
        fromServer.read(senderUser);
        String senderName = new String(senderUser, StandardCharsets.UTF_8);
        fromServer.readChar();
        int messageSize = fromServer.readInt();
        fromServer.readChar();
        byte[] message = new byte[messageSize];
        fromServer.read(message);
        String backMessage = new String(message, StandardCharsets.UTF_8);
        PrintLogUtil.groupMessage(senderName,backMessage);

      }
      case FAILED_MESSAGE -> {
        fromServer.readChar();
        int messageSize = fromServer.readInt();
        fromServer.readChar();
        byte[] message = new byte[messageSize];
        fromServer.read(message);
        String backMessage = new String(message, StandardCharsets.UTF_8);
        PrintLogUtil.errorMessage(this.userName,backMessage);

      }
    }
  }

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
      PrintLogUtil.successMessage(userName,backMessage);
    } else {
      PrintLogUtil.errorMessage(userName,backMessage);
    }
    return successStatus;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


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

  @Override
  public int hashCode() {
    return Objects.hash(userName, fromServer);
  }

  @Override
  public String toString() {
    return "OutputHandler{" +
        "userName='" + userName + '\'' +
        ", fromServer=" + fromServer +
        '}';
  }
}
