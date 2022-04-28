package client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import protocol.MessageType;
import protocol.Protocol;

class ReaderThreadTest {

  ServerSocket serverSocket;

  Socket socket;
  Socket server;
  Client client;
  CountDownLatch readerLatch;
  DataOutputStream serverOut;
  ReaderThread r1;
  char space = ' ';
  String message = "hi";
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  void setup() throws IOException {
    serverSocket = new ServerSocket(12347);
    socket = new Socket("127.0.0.1", 12347);
    server = serverSocket.accept();
    client = new Client();
    readerLatch = new CountDownLatch(1);
    r1 = new ReaderThread(client, readerLatch, socket);
    serverOut = new DataOutputStream(server.getOutputStream());
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  void clean() throws IOException {
    socket.close();
    serverSocket.close();
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  void run() throws IOException {
    serverOut.writeInt(Protocol.messageToIdr.get(MessageType.CONNECT_RESPONSE));
    serverOut.writeChar(space);
    serverOut.writeBoolean(true);
    serverOut.writeChar(space);
    serverOut.writeInt(message.length());
    serverOut.writeChar(space);
    serverOut.write(message.getBytes(StandardCharsets.UTF_8));
    r1.run();
    assertTrue(client.isLogOff());
    assertEquals(0, readerLatch.getCount());
    socket.close();
  }

  @Test
  void testEquals() {
    assertTrue(r1.equals(r1));
  }

  @Test
  void testEquals1() {
    assertFalse(r1.equals(8));
  }

  @Test
  void testEquals2() {
    assertFalse(r1.equals(null));
  }

  @Test
  void testEqual3() throws IOException {
    ReaderThread i2 = new ReaderThread(new Client(), readerLatch, socket);
    assertTrue(r1.equals(i2));
  }

  @Test
  void testHashCode() {
    assertEquals(r1.hashCode(), r1.hashCode());

  }

  @Test
  void testHashCode1() throws IOException {
    ReaderThread i2 = new ReaderThread(new Client(), readerLatch, socket);
    assertTrue(i2.hashCode() == r1.hashCode());

  }


  @Test
  void testToString() throws IOException {

    String result = "ReaderThread{}";

    assertEquals(result, r1.toString());
  }
}