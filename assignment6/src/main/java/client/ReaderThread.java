package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import protocol.MessageType;
import protocol.Protocol;


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
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReaderThread that = (ReaderThread) o;
    return isDisconnect == that.isDisconnect && Objects.equals(client, that.client)
        && Objects.equals(socket, that.socket) && Objects.equals(readerLatch,
        that.readerLatch) && Objects.equals(outputHandler, that.outputHandler)
        && Objects.equals(fromServer, that.fromServer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(client, socket, readerLatch, outputHandler, fromServer, isDisconnect);
  }

  @Override
  public String toString() {
    return "ReaderThread{" +
        "client=" + client +
        ", socket=" + socket +
        ", readerLatch=" + readerLatch +
        ", outputHandler=" + outputHandler +
        ", fromServer=" + fromServer +
        ", isDisconnect=" + isDisconnect +
        '}';
  }
}
