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
  private CountDownLatch readerLatch;

  private  DataInputStream fromServer;

  public ReaderThread(Client client,CountDownLatch readerLatch,Socket socket)
      throws IOException {
    this.client = client;
    this.readerLatch = readerLatch;
    this.fromServer = new DataInputStream(socket.getInputStream());
  }

  public void run() {
    while (true) {
      try {
        if (fromServer.available() > 0 ) {
              int messageType = fromServer.readInt();
          OutputHandler outputHandler = new OutputHandler(client.getUserName(), fromServer);
              if (Protocol.idrToMessage.get(messageType) == MessageType.CONNECT_RESPONSE) {
                boolean isDisconnect = outputHandler.connectStatusResponseHandle();
                if (isDisconnect) {
                  this.client.setLogOff(isDisconnect);
                  break;
                }
              } else {
                outputHandler.outPuthandle(Protocol.idrToMessage.get(messageType));
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
    return Objects.equals(client, that.client);
  }

  @Override
  public int hashCode() {
    return Objects.hash(client);
  }

  @Override
  public String toString() {
    return "ReaderThread{}";
  }
}
