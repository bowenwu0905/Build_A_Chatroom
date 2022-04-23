package server;

import assignment4.Grammar;
import assignment4.JsonReader;
import com.sun.source.tree.Scope;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
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
  private Grammar grammar;
  private JsonReader jsonReader;
  private byte[] buffer;

  public ServerHandler(Semaphore semaphore, ServerSocket serverSocket) {
    this.semaphore = semaphore;
    this.serverSocket = serverSocket;
    protocol = new ProtocolImp();
    grammar = new Grammar();
    jsonReader = new JsonReader();
  }

  @Override
  public void run() {
    while (true) {
      try {
        this.semaphore.acquire();
        Socket socket = this.serverSocket.accept();
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        int identifier = in.readInt();
        switch (ProtocolImp.idrToMessage.get(identifier)) {
          case CONNECT_MESSAGE -> {
            int size = in.readInt();
            buffer = new byte[size];
            in.read(buffer, 0, size);
            String username = new String(buffer);
            boolean status = false;
            String response = null;
            if (!socketMap.containsKey(username)) {
              socketMap.put(username, socket);
              // success
              response = "There are " + socketMap.size() + " other connected clients";
              status = true;
            } else {
              // fail
              response = "Already have established the connection";
            }
            protocol.encode(MessageType.CONNECT_RESPONSE, List.of(String.valueOf(status), response));
            // todo
            out.write(protocol.encode(MessageType.CONNECT_RESPONSE, response));
          }
          case DISCONNECT_MESSAGE -> {
            String username = protocol.decode(type, line);
            Socket socket1 = socketMap.get(username);
            socket1.close();
            socketMap.remove(username);
          }
          case BROADCAST_MESSAGE -> {
          }
          case SEND_INSULT -> {
            String insultMessage = grammar.textGenerator("start", jsonReader.jsonProcess());

          }
          case QUERY_USERS -> {
            String username = protocol.decode(type, line);
            Socket socket1 = socketMap.get(username);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.write(protocol.encode(MessageType.QUERY_RESPONSE, username));
          }

        }

        while (true) {

          MessageType type = protocol.getMessageType(line);
          switch (type) {
            case BROADCAST_MESSAGE -> {
            }
            case SEND_INSULT -> {
              String insultMessage = grammar.textGenerator("start", jsonReader.jsonProcess());

            }
            case CONNECT_MESSAGE -> {

              socketMap.put(protocol.decode(type, line), socket);
              // success
              String reponse = "" + socketMap.size();
              // fail

              out.write(protocol.encode(MessageType.CONNECT_RESPONSE, reponse));
            }
            case QUERY_USERS -> {
              String username = protocol.decode(type, line);
              Socket socket1 = socketMap.get(username);

              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              out.write(protocol.encode(MessageType.QUERY_RESPONSE, username));
            }
            case DISCONNECT_MESSAGE -> {
              String username = protocol.decode(type, line);
              Socket socket1 = socketMap.get(username);
              socket1.close();
              socketMap.remove(username);
            }
          }

          if (socketMap.size() == 0)
            break;

          in.close();

          semaphore.release();
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}