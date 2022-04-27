package client;

import static org.junit.jupiter.api.Assertions.*;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterThreadTest {

  ServerSocket serverSocket;
  WriterThread w1;
  Socket socket;
  Socket server;
  Client client;
  CountDownLatch readerLatch;
  DataInputStream serverIn;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  void setup() throws IOException {
    serverSocket = new ServerSocket(12346);
    socket = new Socket("127.0.0.1", 12346);
    server = serverSocket.accept();
    client = new Client();
    readerLatch = new CountDownLatch(1);
    w1 = new WriterThread(client, readerLatch, socket);
    serverIn = new DataInputStream(server.getInputStream());

    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));

  }

  @AfterEach
  public void restoreStreams() throws IOException {
    serverSocket.close();
    socket.close();
    System.setOut(originalOut);
    System.setErr(originalErr);
  }


  @Test
  void run() throws IOException {

  }

  @Test
  void testEquals() {
    assertTrue(w1.equals(w1));
  }

  @Test
  void testEquals1() {
    assertFalse(w1.equals(8));
  }

  @Test
  void testEquals2() {
    assertFalse(w1.equals(null));
  }

  @Test
  void testEqual3() throws IOException {
    WriterThread i2 = new WriterThread(new Client(), readerLatch, socket);
    assertTrue(w1.equals(i2));
  }

  @Test
  void testHashCode() {
    assertEquals(w1.hashCode(), w1.hashCode());

  }

  @Test
  void testHashCode1() throws IOException {
    WriterThread i2 = new WriterThread(new Client(), readerLatch, socket);
    assertTrue(i2.hashCode() == w1.hashCode());

  }


  @Test
  void testToString() {
    assertEquals("WriterThread{}", w1.toString());
  }
}