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

  private Semaphore semaphore;
  private ConcurrentHashMap<String, Socket> socketMap;
  private ConcurrentHashMap<String, DataOutputStream> outMap;

  @BeforeEach
  void setUp() throws IOException {
    semaphore = new Semaphore(10);
    serverSocket = new ServerSocket(12345);
    socket = new Socket("127.0.0.1", 12345);
    socketMap = new ConcurrentHashMap<>(10);
    outMap = new ConcurrentHashMap<>(10);
    serverHandler = new ServerHandler(semaphore, socket, socketMap, outMap);
    String filePath = new File("").getAbsolutePath();
    String fileDestination = filePath.concat("/src/test/java/server/file.txt");
    in = new DataInputStream(
        new FileInputStream(fileDestination));
    out = new DataOutputStream(
        new FileOutputStream(fileDestination));
  }

//  @Test
//  void run() throws IOException {
//    out = new DataOutputStream(socket.getOutputStream());
//    serverHandler.getProtocol().encode(MessageType.DISCONNECT_MESSAGE, List.of("abc"), out);
//    serverHandler.run();
//    assertTrue(serverHandler.getSocket().isClosed());
//  }

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
    in.readInt();
    in.readChar();
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
    in.readInt();
    in.readChar();
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
    in.readInt();
    in.readChar();
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
  void getString() throws IOException {
    serverHandler.getProtocol().encode(MessageType.CONNECT_MESSAGE, List.of("abc"), out);
    in.readInt();
    in.readChar();
    int size = in.readInt();
    String username = serverHandler.getString(in, size);
    assertEquals("abc", username);
  }

  @Test
  void sendFailedMessage() throws IOException {
    serverHandler.sendFailedMessage("abc", out);
    assertFalse(socket.isClosed());
  }

  @Test
  void testToString() {
    assertNotNull(serverHandler.toString());
  }

  @Test
  void testEquals() {
    assertTrue(serverHandler.equals(serverHandler));
    assertFalse(serverHandler.equals("abc"));
    ServerHandler serverHandler1 = new ServerHandler(semaphore, socket, socketMap, outMap);
    serverHandler1.getOutMap().put("a2", out);
    serverHandler1.getSocketMap().put("a2", socket);
    assertTrue(serverHandler1.equals(serverHandler));
  }

  @Test
  void testEquals2() throws FileNotFoundException {
    ServerHandler serverHandler1 = new ServerHandler(semaphore, socket, socketMap, outMap);
    serverHandler1.getOutMap().put("a2", out);
    assertTrue(serverHandler1.equals(serverHandler));
    ServerHandler serverHandler2 = new ServerHandler(new Semaphore(5), socket, socketMap, outMap);
    assertFalse(serverHandler2.equals(serverHandler));
    ServerHandler serverHandler3 = new ServerHandler(semaphore, new Socket(), socketMap, outMap);
    assertFalse(serverHandler3.equals(serverHandler));
    ConcurrentHashMap<String, Socket> map = new ConcurrentHashMap<>(1);
    map.put("a", new Socket());
    ServerHandler serverHandler4 = new ServerHandler(semaphore, socket, map, outMap);
    assertFalse(serverHandler4.equals(serverHandler));
    String filePath = new File("").getAbsolutePath();
    String fileDestination = filePath.concat("/src/test/java/server/file.txt");
    ConcurrentHashMap<String, DataOutputStream> outputStreamConcurrentHashMap = new ConcurrentHashMap<>();
    outputStreamConcurrentHashMap.put("a", new DataOutputStream(new FileOutputStream(fileDestination)));
    ServerHandler serverHandler5 = new ServerHandler(semaphore, socket, socketMap, outputStreamConcurrentHashMap);
    assertFalse(serverHandler5.equals(serverHandler));
  }

  @Test
  void testHashCode() {
    outMap.put("abc", out);
    socketMap.put("abc", socket);
    ServerHandler serverHandler1 = new ServerHandler(semaphore, socket, socketMap, outMap);
    assertNotEquals(serverHandler1.hashCode(), serverHandler.hashCode());
  }

  @AfterEach
  void tearDown() throws IOException {
    socket.close();
    serverSocket.close();
  }
}