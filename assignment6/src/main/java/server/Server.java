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

  private static final int CLIENT_LIMIT = 10;
  private static final int port = 10000;
  private ServerSocket serverSocket;
  private Semaphore semaphore;
  private ConcurrentHashMap<String, Socket> socketMap;
  private ConcurrentHashMap<String, DataOutputStream> outMap;

  /**
   * create a new server
   */
  public Server() {
  }

  /**
   * main class
   *
   * @param args input arguments
   * @throws IOException IOException
   */
  public static void main(String[] args) throws IOException {
    Server server = new Server();
    server.start(port);
    server.run();
  }

  /**
   * start server
   *
   * @param port int
   * @throws IOException when server socket failed
   */
  public void start(int port) throws IOException {
    semaphore = new Semaphore(CLIENT_LIMIT);
    socketMap = new ConcurrentHashMap<>(CLIENT_LIMIT);
    outMap = new ConcurrentHashMap<>(CLIENT_LIMIT);
    serverSocket = new ServerSocket(port);
    System.out.println("Server start to listen port " + port);
  }

  /**
   * server start listening
   *
   * @throws IOException server throw exception
   */
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

  /**
   * get server socket
   *
   * @return ServerSocket
   */
  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  /**
   * set server socket
   *
   * @param serverSocket ServerSocket
   */
  public void setServerSocket(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  /**
   * get semaphore
   *
   * @return Semaphore
   */
  public Semaphore getSemaphore() {
    return semaphore;
  }

  /**
   * set semaphore
   *
   * @param semaphore Semaphore
   */
  public void setSemaphore(Semaphore semaphore) {
    this.semaphore = semaphore;
  }

  /**
   * get socket map
   *
   * @return ConcurrentHashMap
   */
  public ConcurrentHashMap<String, Socket> getSocketMap() {
    return socketMap;
  }

  /**
   * set socket map
   *
   * @param socketMap ConcurrentHashMap String, Socket
   */
  public void setSocketMap(
      ConcurrentHashMap<String, Socket> socketMap) {
    this.socketMap = socketMap;
  }

  /**
   * get out map
   *
   * @return ConcurrentHashMap String, DataOutputStream
   */
  public ConcurrentHashMap<String, DataOutputStream> getOutMap() {
    return outMap;
  }

  /**
   * set out map
   *
   * @param outMap ConcurrentHashMap String, DataOutputStream
   */
  public void setOutMap(
      ConcurrentHashMap<String, DataOutputStream> outMap) {
    this.outMap = outMap;
  }

  /**
   * equals method
   *
   * @param o Object
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Server server)) {
      return false;
    }
    return Objects.equals(getServerSocket(), server.getServerSocket())
        && Objects.equals(getSemaphore(), server.getSemaphore())
        && Objects.equals(getSocketMap(), server.getSocketMap())
        && Objects.equals(getOutMap(), server.getOutMap());
  }

  /**
   * get hash code
   *
   * @return int
   */
  @Override
  public int hashCode() {
    return Objects.hash(getServerSocket(), getSemaphore(), getSocketMap(), getOutMap());
  }

  /**
   * to string method
   *
   * @return String
   */
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