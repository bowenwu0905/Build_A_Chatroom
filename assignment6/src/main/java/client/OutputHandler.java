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

      case CONNECT_RESPONSE -> {
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
          break;
        } else {
          PrintLogUtil.errorMessage(userName,backMessage);
        }

      }

      case DISCONNECT_RESPONSE -> {



      }

      case QUERY_RESPONSE -> {

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

}
