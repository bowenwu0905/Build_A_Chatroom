package server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
class ServerTest {

  private ServerSocket serverSocket;
  private Socket socket;

  private DataInputStream in;
  private DataOutputStream out;

  private Semaphore semaphore;
  private ConcurrentHashMap<String, Socket> socketMap;
  private ConcurrentHashMap<String, DataOutputStream> outMap;

  private Server server;

  @BeforeEach
  void setUp() throws IOException {
    semaphore = new Semaphore(10);
    serverSocket = new ServerSocket(12345);
    socket = new Socket("127.0.0.1", 12345);
    socketMap = new ConcurrentHashMap<>(10);
    outMap = new ConcurrentHashMap<>(10);
    server = new Server();
  }

  @Test
  void start() throws IOException {
    server.start(12342);
    assertEquals(12342, server.getServerSocket().getLocalPort());
  }

  @Test
  void run() {
  }

  @Test
  void getServerSocket() {
    assertNull(server.getServerSocket());
  }

  @Test
  void setServerSocket() {
    server.setServerSocket(serverSocket);
    assertEquals(12345, server.getServerSocket().getLocalPort());
  }

  @Test
  void getSemaphore() {
    assertNull(server.getSemaphore());
  }

  @Test
  void setSemaphore() {
    semaphore = new Semaphore(1);
    server.setSemaphore(semaphore);
    assertEquals(1, server.getSemaphore().availablePermits());
  }

  @Test
  void getSocketMap() {
    assertNull(server.getSocketMap());
  }

  @Test
  void setSocketMap() {
    socketMap.put("a", socket);
    server.setSocketMap(socketMap);
    assertEquals(1, server.getSocketMap().size());
  }

  @Test
  void getOutMap() {
    assertNull(server.getOutMap());
  }

  @Test
  void setOutMap() {
    server.setOutMap(outMap);
    assertEquals(0, server.getOutMap().size());
  }

  @Test
  void main() {
  }

  @Test
  void testEquals() {
    Server server1 = new Server();
    assertEquals(server1, server);
    assertEquals(server1, server1);
    assertNotEquals(null, server1);
  }

  @Test
  void testEquals2() throws FileNotFoundException {
    Server server1 = new Server();
    server1.setSemaphore(new Semaphore(5));
    assertFalse(server1.equals(server));
    Server server2 = new Server();
    String filePath = new File("").getAbsolutePath();
    String fileDestination = filePath.concat("/src/test/java/server/file.txt");
    ConcurrentHashMap<String, DataOutputStream> outputStreamConcurrentHashMap = new ConcurrentHashMap<>();
    outputStreamConcurrentHashMap.put("a", new DataOutputStream(new FileOutputStream(fileDestination)));
    server2.setOutMap(outputStreamConcurrentHashMap);
    assertFalse(server2.equals(server));
  }

  @Test
  void testEquals3() throws IOException {
    Server server1 = new Server();
    server1.setServerSocket(new ServerSocket());
    assertFalse(server1.equals(server));
    Server server2 = new Server();
    ConcurrentHashMap<String, Socket> map = new ConcurrentHashMap<>(1);
    map.put("a", new Socket());
    server2.setSocketMap(map);
    assertFalse(server2.equals(server));
  }

  @Test
  void testHashCode() {
    Server server1 = new Server();
    assertEquals(server1.hashCode(), server.hashCode());
  }

  @Test
  void testToString() {
    assertEquals("Server{serverSocket=null, semaphore=null, socketMap=null, outMap=null}", server.toString());
  }

  @AfterEach
  void tearDown() throws IOException {
    socket.close();
    serverSocket.close();
  }
}