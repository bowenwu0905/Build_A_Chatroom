package server;

import client.Client;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import protocol.Protocol;

/**
 * server class
 *
 * @author xiaochong
 */
public class Server {
  private ServerSocket serverSocket;
  private Semaphore semaphore;
  private Protocol protocol;
  private ConcurrentHashMap<String, Socket> socketMap;

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    System.out.println("Server start to listen port " + port);
    semaphore = new Semaphore(10);
  }

  public void run() throws IOException, InterruptedException {
    // basically, we want to limit 10 clients, we need to set a thread pool that
    // only process 10 socket connection
    while (true) {
      this.semaphore.acquire();
      Socket socket = this.serverSocket.accept();
      new Thread(new ServerHandler(semaphore, socket, socketMap)).start();
    }
  }

  public void stopServer() throws IOException {
    serverSocket.close();
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    ConcurrentHashMap<Integer, Socket> socketMap = new ConcurrentHashMap<>(10);
    Server server = new Server();
    server.start(10000);
    server.run();
    server.stopServer();
  }

}