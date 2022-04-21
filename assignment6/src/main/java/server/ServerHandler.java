package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import protocol.MessageType;
import protocol.Protocol;
import protocol.ProtocolImp;

/**
 * Server handler class handle messages received
 *
 * @author xiaochong
 */
public class ServerHandler implements Runnable {
  private Semaphore semaphore;
  private ServerSocket serverSocket;
  private Protocol protocol;
  private ConcurrentHashMap<String, Socket> socketMap;

  public ServerHandler(Semaphore semaphore, ServerSocket serverSocket) {
    this.semaphore = semaphore;
    this.serverSocket = serverSocket;
  }

  @Override
  public void run() {
    try {
      this.semaphore.acquire();
      Socket socket  = this.serverSocket.accept();
      socketMap.put(, socket);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      String line;
      // !line.equals(logoff)

      while ((line = in.readLine()) != null) {
        if (line.equals(""))
          break;
        protocol = new ProtocolImp();
        MessageType type = protocol.getMessageType(line);
        protocol.decode(type, line);

      }
      in.close();
      socket.close();
      socketMap.remove();
      semaphore.release();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
  }
}