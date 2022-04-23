package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        fromServer.readChar();
        int numberOfUsers = fromServer.readInt();
        if(numberOfUsers>0){
          fromServer.readChar();
          for( int i =0 ; i<numberOfUsers;i++){

          }

        }

      }
      case DIRECT_MESSAGE -> {

      }
      case BROADCAST_MESSAGE -> {

      }
      case FAILED_MESSAGE -> {

      }
      case SEND_INSULT -> {

      }
    }


  }

  public boolean connectStatusResponseHandle() throws IOException {
    fromServer.readChar();
    Boolean successStatus = fromServer.readBoolean();
    fromServer.readChar();
    int messageSize = fromServer.readInt();
    fromServer.readChar();
    byte[] message = new byte[messageSize];
    fromServer.read(message);
    String backMessage = new String(message, StandardCharsets.UTF_8);
    if (successStatus) {
      PrintLogUtil.successMessage(userName,backMessage);
      return successStatus;
    } else {
      PrintLogUtil.errorMessage(userName,backMessage);
      return successStatus;
    }
  }


}
