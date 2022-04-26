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
import java.util.Objects;
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

  private final Semaphore semaphore;
  private final Socket socket;
  private final Protocol protocol = new ProtocolImp();
  private final ConcurrentHashMap<String, Socket> socketMap;
  private final ConcurrentHashMap<String, DataOutputStream> outMap;
  private final Grammar grammar = new Grammar();
  private final JsonReader jsonReader = new JsonReader();


  public ServerHandler(Semaphore semaphore, Socket socket,
      ConcurrentHashMap<String, Socket> socketMap,
      ConcurrentHashMap<String, DataOutputStream> outMap) {
    this.semaphore = semaphore;
    this.socket = socket;
    this.socketMap = socketMap;
    this.outMap = outMap;
  }

  public Semaphore getSemaphore() {
    return semaphore;
  }

  public Socket getSocket() {
    return socket;
  }

  public Protocol getProtocol() {
    return protocol;
  }

  public ConcurrentHashMap<String, Socket> getSocketMap() {
    return socketMap;
  }

  public ConcurrentHashMap<String, DataOutputStream> getOutMap() {
    return outMap;
  }

  public Grammar getGrammar() {
    return grammar;
  }

  public JsonReader getJsonReader() {
    return jsonReader;
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
    while (!socket.isClosed()) {
      try {
        int identifier = in.readInt();
        in.readChar();
        switch (ProtocolImp.idrToMessage.get(identifier)) {
          case CONNECT_MESSAGE -> connectMessage(in, out);
          case DISCONNECT_MESSAGE -> disconnectMessage(in, out);
          case QUERY_USERS -> queryUser(in, out);
          case BROADCAST_MESSAGE -> broadcastMessage(in, out);
          case DIRECT_MESSAGE -> directMessage(in, out);
          case SEND_INSULT -> sendInsult(in, out);
          default -> protocol.encode(MessageType.FAILED_MESSAGE, List.of("wrong input"), out);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void connectMessage(DataInputStream in, DataOutputStream out) throws IOException {
    int size = in.readInt();
    String username = getString(in, size);
    boolean status = false;
    String response;
    if (!socketMap.containsKey(username)) {
      socketMap.put(username, socket);
      outMap.put(username, out);
      // success
      response = "There are " + socketMap.size() + " connected clients";
      status = true;
    } else {
      // fail
      response = username + " already has established the connection";
    }
    protocol.encode(MessageType.CONNECT_RESPONSE, List.of(String.valueOf(status), response),
        out);
  }

  private void disconnectMessage(DataInputStream in, DataOutputStream out) throws IOException {
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
    protocol.encode(MessageType.CONNECT_RESPONSE,
        List.of(String.valueOf(status), response), out);
    socket.close();
    outMap.remove(username);
    socketMap.remove(username);
    semaphore.release();
  }

  private void queryUser(DataInputStream in, DataOutputStream out) throws IOException {
    int size = in.readInt();
    String username = getString(in, size);
    if (socketMap.containsKey(username)) {
      List<String> userList = new ArrayList<>();
      for (Entry<String, Socket> entry : socketMap.entrySet()) {
        if (!entry.getKey().equals(username)) {
          userList.add(entry.getKey());
        }
      }
      protocol.encode(MessageType.QUERY_RESPONSE, userList, out);
    } else {
      sendFailedMessage(username, out);
    }
  }

  private void broadcastMessage(DataInputStream in, DataOutputStream out) throws IOException {
    int size = in.readInt();
    String username = getString(in, size);
    in.readChar();
    int messageSize = in.readInt();
    String message = getString(in, messageSize);
    if (socketMap.containsKey(username)) {
      for (Entry<String, DataOutputStream> entry : outMap.entrySet()) {
        protocol.encode(MessageType.DIRECT_MESSAGE,
            List.of(username, entry.getKey(), message), entry.getValue());
      }
    } else {
      sendFailedMessage(username, out);
    }
  }

  private void directMessage(DataInputStream in, DataOutputStream out) throws IOException {
    int senderSize = in.readInt();
    String sender = getString(in, senderSize);
    in.readChar();
    int recipientSize = in.readInt();
    String recipient = getString(in, recipientSize);
    in.readChar();
    int messageSize = in.readInt();
    String message = getString(in, messageSize);
    if (socketMap.containsKey(sender) && socketMap.containsKey(recipient)) {
      DataOutputStream dataOutputStream = outMap.get(recipient);
      protocol.encode(MessageType.DIRECT_MESSAGE, List.of(sender, recipient, message),
          dataOutputStream);
    } else {
      String tmp = !socketMap.containsKey(sender) ? sender : recipient;
      sendFailedMessage(tmp, out);
    }
  }

  private void sendInsult(DataInputStream in, DataOutputStream out) throws Exception {
    int senderSize = in.readInt();
    String sender = getString(in, senderSize);
    in.readChar();
    int recipientSize = in.readInt();
    String recipient = getString(in, recipientSize);
    String response;
    if (socketMap.containsKey(sender) && socketMap.containsKey(recipient)) {
      response = grammar.textGenerator("start",
          jsonReader.jsonProcess("templates/insult_grammar.json"));
      for (Entry<String, DataOutputStream> entry : outMap.entrySet()) {
        protocol.encode(MessageType.DIRECT_MESSAGE, List.of(sender, recipient, response),
            entry.getValue());
      }
    } else {
      String tmp = !socketMap.containsKey(sender) ? sender : recipient;
      sendFailedMessage(tmp, out);
    }
  }

  private String getString(DataInputStream in, int size) throws IOException {
    in.readChar();
    byte[] buffer = new byte[size];
    in.read(buffer, 0, size);
    return new String(buffer, StandardCharsets.UTF_8);
  }

  private void sendFailedMessage(String client, DataOutputStream out) throws IOException {
    String response = client + " hasn't set the connection";
    protocol.encode(MessageType.FAILED_MESSAGE, List.of(response), out);
  }

  @Override
  public String toString() {
    return "ServerHandler{" +
        "semaphore=" + semaphore +
        ", socket=" + socket +
        ", protocol=" + protocol +
        ", socketMap=" + socketMap +
        ", outMap=" + outMap +
        ", grammar=" + grammar +
        ", jsonReader=" + jsonReader +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ServerHandler)) {
      return false;
    }
    ServerHandler that = (ServerHandler) o;
    return Objects.equals(getSemaphore(), that.getSemaphore()) && Objects.equals(
        getSocket(), that.getSocket()) && Objects.equals(getProtocol(), that.getProtocol())
        && Objects.equals(getSocketMap(), that.getSocketMap()) && Objects.equals(
        getOutMap(), that.getOutMap()) && Objects.equals(getGrammar(), that.getGrammar())
        && Objects.equals(getJsonReader(), that.getJsonReader());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSemaphore(), getSocket(), getProtocol(), getSocketMap(), getOutMap(),
        getGrammar(), getJsonReader());
  }
}