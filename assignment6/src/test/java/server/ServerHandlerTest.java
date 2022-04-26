package server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xiaochong
 */
class ServerHandlerTest {
  private ServerHandler serverHandler;
  private ServerSocket serverSocket;
  private Socket socket;


  @BeforeEach
  void setUp() throws IOException {
    Semaphore semaphore = new Semaphore(10);
    serverSocket = new ServerSocket(12345);
    socket = new Socket("127.0.0.1", 12345);
    ConcurrentHashMap<String, Socket> socketMap = new ConcurrentHashMap<>(10);
    ConcurrentHashMap<String, DataOutputStream> outMap = new ConcurrentHashMap<>(10);
    serverHandler = new ServerHandler(semaphore, socket, socketMap, outMap);
  }

  @Test
  void getSemaphore() {
    assertEquals(10, serverHandler.getSemaphore().availablePermits());
  }

  @Test
  void getSocket() {
    assertEquals(false, serverHandler.getSocket().isClosed());
  }

  @Test
  void getProtocol() {
    assertNotNull(serverHandler.getProtocol());
  }

  @Test
  void getSocketMap() {
    assertEquals(0, serverHandler.getSocketMap().size());
  }

  @Test
  void getOutMap() {
    assertEquals(0, serverHandler.getOutMap().size());
  }

  @Test
  void getGrammar() {
    assertNotNull(serverHandler.getGrammar());
  }

  @Test
  void getJsonReader() {
    assertNotNull(serverHandler.getJsonReader());
  }

  @Test
  void connectMessage() throws FileNotFoundException {
    DataInputStream in = new DataInputStream(
        new FileInputStream("/src/test/java/server/file.txt"));
    DataOutputStream out = new DataOutputStream(
        new FileOutputStream("/src/test/java/server/file.txt"));
  }

  @Test
  void disconnectMessage() throws FileNotFoundException {
    DataOutputStream out = new DataOutputStream(
        new FileOutputStream("/src/test/java/server/connectMessage.txt"));
    out.writeInt();
    out.writeChar();
    out.writeInt();
    out.writeChar();
    out.writeBytes();
  }

  @AfterEach
  void tearDown() throws IOException {
    socket.close();
    serverSocket.close();
  }
}