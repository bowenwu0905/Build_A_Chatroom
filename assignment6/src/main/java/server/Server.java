package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * server class
 *
 * @author xiaochong
 */
public class Server {

  private ServerSocket serverSocket;
  private Semaphore semaphore;
  private ConcurrentHashMap<String, Socket> socketMap;

  private ConcurrentHashMap<String, DataOutputStream> outMap;

  private static final int CLIENT_LIMIT = 10;

  public Server() {
  }

  public void start(int port) throws IOException {
    semaphore = new Semaphore(CLIENT_LIMIT);
    socketMap = new ConcurrentHashMap<>(CLIENT_LIMIT);
    outMap = new ConcurrentHashMap<>(CLIENT_LIMIT);
    serverSocket = new ServerSocket(port);
    System.out.println("Server start to listen port " + port);
  }

  public void run() throws IOException {
    // basically, we want to limit 10 clients, we need to set a thread pool that
    // only process 10 socket connection
    try {
      while (true) {
        this.semaphore.acquire();
        Socket socket = this.serverSocket.accept();
        new Thread(new ServerHandler(semaphore, socket, socketMap, outMap)).start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      serverSocket.close();
    }
  }

  public static void main(String[] args) throws IOException {
    Server server = new Server();
    // 需要从command输入么?
    server.start(10000);
    server.run();
  }

  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  public void setServerSocket(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  public Semaphore getSemaphore() {
    return semaphore;
  }

  public void setSemaphore(Semaphore semaphore) {
    this.semaphore = semaphore;
  }

  public ConcurrentHashMap<String, Socket> getSocketMap() {
    return socketMap;
  }

  public void setSocketMap(
      ConcurrentHashMap<String, Socket> socketMap) {
    this.socketMap = socketMap;
  }

  public ConcurrentHashMap<String, DataOutputStream> getOutMap() {
    return outMap;
  }

  public void setOutMap(
      ConcurrentHashMap<String, DataOutputStream> outMap) {
    this.outMap = outMap;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Server)) {
      return false;
    }
    Server server = (Server) o;
    return Objects.equals(getServerSocket(), server.getServerSocket())
        && Objects.equals(getSemaphore(), server.getSemaphore())
        && Objects.equals(getSocketMap(), server.getSocketMap())
        && Objects.equals(getOutMap(), server.getOutMap());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getServerSocket(), getSemaphore(), getSocketMap(), getOutMap());
  }

  @Override
  public String toString() {
    return "Server{" +
        "serverSocket=" + serverSocket +
        ", semaphore=" + semaphore +
        ", socketMap=" + socketMap +
        ", outMap=" + outMap +
        '}';
  }
}