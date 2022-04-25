package server;

import assignment4.Grammar;
import assignment4.JsonReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
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
  private Socket socket;
  private Protocol protocol = new ProtocolImp();
  private ConcurrentHashMap<String, Socket> socketMap;
  private ConcurrentHashMap<String, DataOutputStream> outMap = new ConcurrentHashMap<>(10);
  private Grammar grammar = new Grammar();
  private JsonReader jsonReader = new JsonReader();


  public ServerHandler(Semaphore semaphore, Socket socket, ConcurrentHashMap<String, Socket> socketMap) {
    this.semaphore = semaphore;
    this.socket = socket;
    this.socketMap = socketMap;
  }

  @Override
  public void run() {
    DataInputStream in;
    DataOutputStream out;
    try {
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    while (true) {
      try {
        int identifier = in.readInt();
        in.readChar();
        switch (ProtocolImp.idrToMessage.get(identifier)) {
          case CONNECT_MESSAGE -> {
            int size = in.readInt();
            String username = getString(in, size);
            boolean status = false;
            String response;
            if (!socketMap.containsKey(username)) {
              socketMap.put(username, socket);
              outMap.put(username, out);
              // success
              response = "There are " + socketMap.size() + " other connected clients";
              status = true;
            } else {
              // fail
              response = "Already have established the connection";
            }
            protocol.encode(MessageType.CONNECT_RESPONSE, List.of(String.valueOf(status), response), out);

          } case DISCONNECT_MESSAGE -> {
            int size = in.readInt();
            String username = getString(in, size);
            boolean status = false;
            String response;
            if (socketMap.containsKey(username)) {
              status = true;
              response = "You are no longer connected.";
            } else {
              response = "this client haven't set the connection";
            }
            protocol.encode(MessageType.DISCONNECT_RESPONSE, List.of(String.valueOf(status), response), out);
            socket.close();
            outMap.remove(username);
            socketMap.remove(username);
            semaphore.release();
            break;
          } case QUERY_USERS -> {
            int size = in.readInt();
            String username = getString(in, size);
            String response = null;
            if (socketMap.containsKey(username)) {
              List<String> userList = new ArrayList<>();
              for (Entry<String, Socket> entry : socketMap.entrySet()) {
                if (!entry.getKey().equals(username))
                  userList.add(entry.getKey());
              }
              protocol.encode(MessageType.QUERY_RESPONSE, userList, out);
            } else {
              response = "this client haven't set the connection";
              protocol.encode(MessageType.FAILED_MESSAGE, List.of(response), out);
            }
          } case BROADCAST_MESSAGE -> {
            int size = in.readInt();
            String username = getString(in, size);
            in.readChar();
            int messageSize = in.readInt();
            String message = getString(in, messageSize);
            String response = null;
            if (socketMap.containsKey(username)) {
              response = message + "\n Message from sender: " + username;
              for (Entry<String, DataOutputStream> entry : outMap.entrySet()) {
                protocol.encode(MessageType.DIRECT_MESSAGE, List.of(username, entry.getKey(), response), entry.getValue());
              }
            } else {
              response = "this client haven't set the connection";
              protocol.encode(MessageType.FAILED_MESSAGE, List.of(response), out);
            }
          } case DIRECT_MESSAGE -> {
            int senderSize = in.readInt();
            String sender = getString(in, senderSize);
            in.readChar();
            int recipientSize = in.readInt();
            String recipient = getString(in, recipientSize);
            in.readChar();
            int messageSize = in.readInt();
            String message = getString(in, messageSize);
            String response = null;
            if (socketMap.containsKey(sender) && socketMap.containsKey(recipient)) {
              DataOutputStream dataOutputStream = outMap.get(recipient);
              protocol.encode(MessageType.DIRECT_MESSAGE, List.of(sender, recipient, message), dataOutputStream);
            } else {
              String tmp = !socketMap.containsKey(sender) ? sender : recipient;
              response = tmp + " haven't set the connection";
              protocol.encode(MessageType.FAILED_MESSAGE, List.of(response), out);
            }
          } case SEND_INSULT -> {
            int senderSize = in.readInt();
            String sender = getString(in, senderSize);
            in.readChar();
            int recipientSize = in.readInt();
            String recipient = getString(in, recipientSize);
            String response = null;
            if (socketMap.containsKey(sender) && socketMap.containsKey(recipient)) {
              response = grammar.textGenerator("start", jsonReader.jsonProcess("templates/insult_grammar.json"));
              DataOutputStream dataOutputStream = outMap.get(recipient);
              for (Entry<String, DataOutputStream> entry : outMap.entrySet()) {
                protocol.encode(MessageType.DIRECT_MESSAGE, List.of(sender, recipient, response), entry.getValue());
              }
            } else {
              String tmp = !socketMap.containsKey(sender) ? sender : recipient;
              response = tmp + " haven't set the connection";
              protocol.encode(MessageType.FAILED_MESSAGE, List.of(response), out);
            }
          } default -> {
            // todo
          }

        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private String getString(DataInputStream in, int size) throws IOException {
    in.readChar();
    byte[] buffer = new byte[size];
    in.read(buffer, 0, size);
    return new String(buffer, StandardCharsets.UTF_8);
  }
}