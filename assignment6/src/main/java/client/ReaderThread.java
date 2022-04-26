package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import protocol.MessageType;
import protocol.Protocol;
import util.Command;

public class ReaderThread extends Thread{
  private Client client;
  private Socket socket;
  private CountDownLatch readerLatch;
  private OutputHandler outputHandler;

  private  DataInputStream fromServer;
  private boolean isDisconnect = false;

  public ReaderThread(Client client,CountDownLatch readerLatch,Socket socket)
      throws IOException {
    this.client = client;
    this.readerLatch = readerLatch;
    this.socket = socket;
    this.fromServer = new DataInputStream(this.socket.getInputStream());


  }
  public void run() {
    while (true) {
      try {
        if (fromServer.available() > 0 ) {

              int messageType = fromServer.readInt();
              this.outputHandler = new OutputHandler(client.getUserName(), fromServer);
              if (Protocol.idrToMessage.get(messageType) == MessageType.CONNECT_RESPONSE) {
                this.isDisconnect = outputHandler.connectStatusResponseHandle();
                if (this.isDisconnect) {
                  this.client.setLogOff(this.isDisconnect);
                  break;
                }
              } else {
                this.outputHandler.outPuthandle(Protocol.idrToMessage.get(messageType));
              }
        }
      } catch (IOException ex) {
      System.err.println("Error reading from server: " + ex.getMessage());
      ex.printStackTrace();
      break;
      }

    }
    this.readerLatch.countDown();
    System.out.println("end of write");


  }

}
