package client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import protocol.MessageType;
import protocol.Protocol;
import protocol.ProtocolImp;
import util.Command;

class InputHandlerTest {
  String userName;
  String receiverName;
  String text;
  DataInputStream fromServer;
  DataOutputStream toServer;

  OutputHandler o1;
  InputHandler i1;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;
  List<String> allUsers = Arrays.asList("u1","u2","u3");

  @BeforeEach
  void setUp() throws FileNotFoundException {
    userName = "u1";
    receiverName = "r1";
    text = "hello";
    String filePath = new File("").getAbsolutePath();
    String fileDestination = filePath.concat("/src/test/java/client/file.txt");
    toServer = new DataOutputStream(new FileOutputStream(fileDestination));
    fromServer = new DataInputStream(new FileInputStream(fileDestination));
    o1 = new OutputHandler(userName,fromServer);
    i1 = new InputHandler(userName,toServer);
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }


  @Test
  void inputParse1() throws IOException {
    i1.inputParse("logoff");
    assertEquals(Protocol.messageToIdr.get(MessageType.DISCONNECT_MESSAGE),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    assertEquals(userName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] senderUser = new byte[userName.length()];
    fromServer.read(senderUser);
    String senderName = new String(senderUser, StandardCharsets.UTF_8);
    assertEquals(userName,senderName);
  }

  @Test
  void inputParse2() throws IOException {
    i1.inputParse("who");
    assertEquals(Protocol.messageToIdr.get(MessageType.QUERY_USERS),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    assertEquals(userName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] senderUser = new byte[userName.length()];
    fromServer.read(senderUser);
    String senderName = new String(senderUser, StandardCharsets.UTF_8);
    assertEquals(userName,senderName);
  }

  @Test
  void inputParse3() throws IOException {
    i1.inputParse("@"+receiverName+" "+text);
    assertEquals(Protocol.messageToIdr.get(MessageType.DIRECT_MESSAGE),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    assertEquals(userName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] senderUser = new byte[userName.length()];
    fromServer.read(senderUser);
    String senderName = new String(senderUser, StandardCharsets.UTF_8);
    assertEquals(userName,senderName);

    assertEquals(' ',fromServer.readChar());
    assertEquals(receiverName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] receiverUser = new byte[receiverName.length()];
    fromServer.read(receiverUser);
    String rName = new String(receiverUser, StandardCharsets.UTF_8);
    assertEquals(receiverName,rName);

    assertEquals(' ',fromServer.readChar());
    assertEquals(text.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] textByte = new byte[text.length()];
    fromServer.read(textByte);
    String m = new String(textByte, StandardCharsets.UTF_8);
    assertEquals(text,m);
  }

  @Test
  void inputParse4() throws IOException {
    i1.inputParse("!"+receiverName);
    assertEquals(Protocol.messageToIdr.get(MessageType.SEND_INSULT),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    assertEquals(userName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] senderUser = new byte[userName.length()];
    fromServer.read(senderUser);
    String senderName = new String(senderUser, StandardCharsets.UTF_8);
    assertEquals(userName,senderName);

    assertEquals(' ',fromServer.readChar());
    assertEquals(receiverName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] receiverUser = new byte[receiverName.length()];
    fromServer.read(receiverUser);
    String rName = new String(receiverUser, StandardCharsets.UTF_8);
    assertEquals(receiverName,rName);

  }




  @Test
  void inputParse5() throws IOException {
    i1.inputParse(text);
    assertEquals(Protocol.messageToIdr.get(MessageType.BROADCAST_MESSAGE),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    assertEquals(userName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] senderUser = new byte[userName.length()];
    fromServer.read(senderUser);
    String senderName = new String(senderUser, StandardCharsets.UTF_8);
    assertEquals(userName,senderName);
    assertEquals(' ',fromServer.readChar());
    int x = fromServer.readInt();
    assertEquals(text.length(),x);
    assertEquals(' ',fromServer.readChar());
    byte[] textByte = new byte[x];
    fromServer.read(textByte);
    String m = new String(textByte, StandardCharsets.UTF_8);
    assertEquals(text,m);
  }


  @Test
  void inputParse6() throws IOException {
    i1.inputParse("?");
    String expected = Command.HELP_MENU;
    assertEquals(expected+"\n",outContent.toString());


  }


  @Test
  void inputParse7() throws IOException {
    text = "!";
    i1.inputParse(text);
    assertEquals(Protocol.messageToIdr.get(MessageType.BROADCAST_MESSAGE),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    assertEquals(userName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] senderUser = new byte[userName.length()];
    fromServer.read(senderUser);
    String senderName = new String(senderUser, StandardCharsets.UTF_8);
    assertEquals(userName,senderName);
    assertEquals(' ',fromServer.readChar());
    int x = fromServer.readInt();
    assertEquals(text.length(),x);
    assertEquals(' ',fromServer.readChar());
    byte[] textByte = new byte[x];
    fromServer.read(textByte);
    String m = new String(textByte, StandardCharsets.UTF_8);
    assertEquals(text,m);
  }

  @Test
  void inputParse8() throws IOException {
    text = "@";
    i1.inputParse(text);
    assertEquals(Protocol.messageToIdr.get(MessageType.BROADCAST_MESSAGE),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    assertEquals(userName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] senderUser = new byte[userName.length()];
    fromServer.read(senderUser);
    String senderName = new String(senderUser, StandardCharsets.UTF_8);
    assertEquals(userName,senderName);
    assertEquals(' ',fromServer.readChar());
    int x = fromServer.readInt();
    assertEquals(text.length(),x);
    assertEquals(' ',fromServer.readChar());
    byte[] textByte = new byte[x];
    fromServer.read(textByte);
    String m = new String(textByte, StandardCharsets.UTF_8);
    assertEquals(text,m);
  }

  @Test
  void inputParse9() throws IOException {
    String text1 = "@all hello";
    i1.inputParse(text1);
    assertEquals(Protocol.messageToIdr.get(MessageType.BROADCAST_MESSAGE),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    assertEquals(userName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] senderUser = new byte[userName.length()];
    fromServer.read(senderUser);
    String senderName = new String(senderUser, StandardCharsets.UTF_8);
    assertEquals(userName,senderName);
    assertEquals(' ',fromServer.readChar());
  }



  @Test
  void connectServer() throws IOException {
    i1.connectServer();
    assertEquals(Protocol.messageToIdr.get(MessageType.CONNECT_MESSAGE),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    assertEquals(userName.length(),fromServer.readInt());
    assertEquals(' ',fromServer.readChar());
    byte[] senderUser = new byte[userName.length()];
    fromServer.read(senderUser);
    String senderName = new String(senderUser, StandardCharsets.UTF_8);
    assertEquals(userName,senderName);

  }

  @Test
  void getUserName() {
    assertEquals(userName,i1.getUserName());
  }




  @Test
  void testEquals() {
    assertTrue(i1.equals(i1));
  }

  @Test
  void testEquals1() {
    assertFalse(i1.equals(8));
  }

  @Test
  void testEquals2() {
  assertFalse(i1.equals(null));
  }
  @Test
  void testEqual3() {
    InputHandler i2 = new InputHandler("xx",toServer);
    assertFalse(i1.equals(i2));
  }

  @Test
  void testHashCode() {
    assertEquals(i1.hashCode(),i1.hashCode());

  }

  @Test
  void testHashCode1() {
    InputHandler i2 = new InputHandler("xx",toServer);
    assertFalse(i2.hashCode()==i1.hashCode());

  }

  @Test
  void testToString() {
    String r = "InputHandler{" +
        "userName='" + userName + '\'' +
        ", toServer=" + toServer +
        '}';
    assertEquals(r,i1.toString());
  }
}