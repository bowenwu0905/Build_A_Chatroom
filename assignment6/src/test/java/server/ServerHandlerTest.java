package server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import protocol.MessageType;
import protocol.Protocol;

/**
 * @author xiaochong
 */
class ServerHandlerTest {
  private ServerHandler serverHandler;
  private ServerSocket serverSocket;
  private Socket socket;

  private DataInputStream in;
  private DataOutputStream out;
  @BeforeEach
  void setUp() throws IOException {
    Semaphore semaphore = new Semaphore(10);
    serverSocket = new ServerSocket(12345);
    socket = new Socket("127.0.0.1", 12345);
    ConcurrentHashMap<String, Socket> socketMap = new ConcurrentHashMap<>(10);
    ConcurrentHashMap<String, DataOutputStream> outMap = new ConcurrentHashMap<>(10);
    serverHandler = new ServerHandler(semaphore, socket, socketMap, outMap);
    String filePath = new File("").getAbsolutePath();
    String fileDestination = filePath.concat("/src/test/java/server/file.txt");
    in = new DataInputStream(
        new FileInputStream(fileDestination));
    out = new DataOutputStream(
        new FileOutputStream(fileDestination));
  }

  @Test
  void getSemaphore() {
    assertEquals(10, serverHandler.getSemaphore().availablePermits());
  }

  @Test
  void getSocket() {
    assertFalse(serverHandler.getSocket().isClosed());
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
  void connectMessage() throws IOException {
    serverHandler.getProtocol().encode(MessageType.CONNECT_MESSAGE, List.of("abc"), out);
    serverHandler.connectMessage(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void connectMessage2() throws IOException {
    serverHandler.getProtocol().encode(MessageType.CONNECT_MESSAGE, List.of("abc"), out);
    in.readInt();
    in.readChar();
    serverHandler.getSocketMap().put("abc", socket);
    serverHandler.connectMessage(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void disconnectMessage() throws IOException {
    serverHandler.getProtocol().encode(MessageType.DISCONNECT_MESSAGE, List.of("abc"), out);
    in.readInt();
    in.readChar();
    serverHandler.getSocketMap().put("abc", socket);
    serverHandler.getOutMap().put("abc", out);
    serverHandler.disconnectMessage(in, out);
    assertTrue(socket.isClosed());
  }

  @Test
  void disconnectMessage2() throws IOException {
    serverHandler.getProtocol().encode(MessageType.DISCONNECT_MESSAGE, List.of("abc"), out);
    serverHandler.disconnectMessage(in, out);
    assertTrue(socket.isClosed());
  }

  @Test
  void queryUser() throws IOException {
    serverHandler.getProtocol().encode(MessageType.QUERY_USERS, List.of("abc"), out);
    serverHandler.queryUser(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void queryUser2() throws IOException {
    serverHandler.getProtocol().encode(MessageType.QUERY_USERS, List.of("abc"), out);
    in.readInt();
    in.readChar();
    serverHandler.getSocketMap().put("abc", socket);
    serverHandler.queryUser(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void broadcastMessage() throws IOException {
    serverHandler.getProtocol().encode(MessageType.BROADCAST_MESSAGE, List.of("abc", "aha"), out);
    serverHandler.broadcastMessage(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void broadcastMessage2() throws IOException {
    serverHandler.getProtocol().encode(MessageType.BROADCAST_MESSAGE, List.of("abc", "aha"), out);
    in.readInt();
    in.readChar();
    serverHandler.getSocketMap().put("abc", socket);
    serverHandler.getOutMap().put("aaa", out);
    serverHandler.broadcastMessage(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void directMessage() throws IOException {
    serverHandler.getProtocol().encode(MessageType.DIRECT_MESSAGE, List.of("a1", "a2", "hello"), out);
    serverHandler.directMessage(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void directMessage2() throws IOException {
    serverHandler.getProtocol().encode(MessageType.DIRECT_MESSAGE, List.of("a1", "a2", "hello"), out);
    in.readInt();
    in.readChar();
    serverHandler.getSocketMap().put("a1", socket);
    serverHandler.getSocketMap().put("a2", socket);
    serverHandler.getOutMap().put("a2", out);
    serverHandler.directMessage(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void sendInsult() throws Exception {
    serverHandler.getProtocol().encode(MessageType.SEND_INSULT, List.of("a1", "a2"), out);
    serverHandler.sendInsult(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void sendInsult2() throws Exception {
    serverHandler.getProtocol().encode(MessageType.SEND_INSULT, List.of("a1", "a2"), out);
    in.readInt();
    in.readChar();
    serverHandler.getSocketMap().put("a1", socket);
    serverHandler.getSocketMap().put("a2", socket);
    serverHandler.getOutMap().put("a2", out);
    serverHandler.sendInsult(in, out);
    assertFalse(socket.isClosed());
  }

  @Test
  void getString() {
  }

  @Test
  void sendFailedMessage() {
  }

  @Test
  void testToString() {
  }

  @Test
  void testEquals() {
  }

  @Test
  void testHashCode() {
  }

  @AfterEach
  void tearDown() throws IOException {
    socket.close();
    serverSocket.close();
  }
}