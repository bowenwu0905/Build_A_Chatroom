package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import protocol.MessageType;
import protocol.Protocol;


/**
 * The thread class for receiving  and printing server's message
 */
public class ReaderThread extends Thread {

  private Client client;
  private CountDownLatch readerLatch;

  private DataInputStream fromServer;

  /**
   * the constructor for the class
   *
   * @param client      the client class, which start the thread
   * @param readerLatch the ReaderThread latch, only count down when user successfully log off.
   * @param socket      the socket
   * @throws IOException the exception when the dataInputStream had issues
   */
  public ReaderThread(Client client, CountDownLatch readerLatch, Socket socket)
      throws IOException {
    this.client = client;
    this.readerLatch = readerLatch;
    this.fromServer = new DataInputStream(socket.getInputStream());
  }

  /**
   * the run function
   */
  public void run() {
    while (true) {
      try {
        //when there is information in stream
        if (fromServer.available() > 0) {
          int messageType = fromServer.readInt();
          OutputHandler outputHandler = new OutputHandler(client.getUserName(), fromServer);
          //when user log off
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
    ReaderThread that = (ReaderThread) o;
    return Objects.equals(client, that.client);
  }

  /**
   * calculate the hashcode of the object
   *
   * @return the hashcode of object
   */
  @Override
  public int hashCode() {
    return Objects.hash(client);
  }

  /**
   * to string
   *
   * @return the string representation
   */
  @Override
  public String toString() {
    return "ReaderThread{}";
  }
}
