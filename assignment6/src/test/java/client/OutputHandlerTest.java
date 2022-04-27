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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import protocol.MessageType;
import protocol.Protocol;
import protocol.ProtocolImp;

class OutputHandlerTest {
  String userName;
  String receiverName;
  String text;
  DataInputStream fromServer;
  DataOutputStream toServer;
  Protocol p1;
  OutputHandler o1;
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
    p1 = new ProtocolImp();
    o1 = new OutputHandler(userName,fromServer);
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  void outPuthandle1() throws IOException {
    p1.encode(MessageType.QUERY_RESPONSE, allUsers,toServer);
    int x = fromServer.readInt();
    o1.outPuthandle(Protocol.idrToMessage.get(x));
    assertEquals("Success: user: "+userName+ " All online user list:"+String.join(", ", allUsers)+"\n\n",outContent.toString());
  }



  @Test
  void outPuthandle2() throws IOException {
    p1.encode(MessageType.DIRECT_MESSAGE, Arrays.asList(userName,receiverName,text),toServer);
    int x = fromServer.readInt();
    o1.outPuthandle(Protocol.idrToMessage.get(x));
    assertEquals(userName+" -> "+receiverName+ " :"+text+"\n\n",outContent.toString());
  }

  @Test
  void outPuthandle4() throws IOException {
    p1.encode(MessageType.FAILED_MESSAGE, Arrays.asList(text),toServer);
    int x = fromServer.readInt();
    o1.outPuthandle(Protocol.idrToMessage.get(x));
    assertEquals("ERROR: user: u1 Issue:"+text+"\n\n",errContent.toString());
  }

  @Test
  void outPuthandle5() throws IOException {
    p1.encode(MessageType.QUERY_RESPONSE, new ArrayList<>(),toServer);
    int x = fromServer.readInt();
    o1.outPuthandle(Protocol.idrToMessage.get(x));
    assertEquals("Success: user: "+userName+ " All online user list:\n\n",outContent.toString());
  }


  @Test
  void connectStatusResponseHandle1() throws IOException {
    p1.encode(MessageType.CONNECT_RESPONSE, Arrays.asList("true","success Message"),toServer);
     fromServer.readInt();
    boolean result = o1.connectStatusResponseHandle();
    assertTrue(result);
    assertEquals("Success: user: u1 message:success Message\n\n",outContent.toString());
  }

  @Test
  void connectStatusResponseHandle2() throws IOException {
    p1.encode(MessageType.CONNECT_RESPONSE, Arrays.asList("false","failure Message"),toServer);
    fromServer.readInt();
    boolean result = o1.connectStatusResponseHandle();
    assertFalse(result);
    assertEquals("ERROR: user: u1 Issue:failure Message\n\n",errContent.toString());
  }

  @Test
  void getUserName() {
    assertEquals(userName,o1.getUserName());
  }

  @Test
  void setUserName() {
    String newName = "new1";
    o1.setUserName(newName);
    assertEquals(newName,o1.getUserName());
  }



  @Test
  void testEquals() {
    assertTrue(o1.equals(o1));
  }

  @Test
  void testEquals1() {
    assertFalse(o1.equals(8));
  }

  @Test
  void testEquals2() {
    assertFalse(o1.equals(null));
  }
  @Test
  void testEqual3() {
    OutputHandler i2 = new OutputHandler("xx",fromServer);
    assertFalse(o1.equals(i2));
  }

  @Test
  void testHashCode() {
    assertEquals(o1.hashCode(),o1.hashCode());

  }

  @Test
  void testHashCode1() {
    OutputHandler i2 = new OutputHandler("xx",fromServer);
    assertFalse(i2.hashCode()==o1.hashCode());

  }

  @Test
  void testToString() {
    String expected = "OutputHandler{" +
        "userName='" + userName + '\'' +
        ", fromServer=" + fromServer +
        '}';
    assertEquals(expected,o1.toString());

  }
}