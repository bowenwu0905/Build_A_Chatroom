package protocol;

import static org.junit.jupiter.api.Assertions.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProtocolImpTest {
  ProtocolImp protocolImp;
  DataOutputStream dataOutputStream;
  DataInputStream dataInputStream;
  List<String> listString;
  Character emptyCharacter;
  String msg;

  @BeforeEach
  void setUp() throws FileNotFoundException {
    protocolImp = new ProtocolImp();
    msg = "HelloWorld";
    emptyCharacter = ' ';
    dataOutputStream = new DataOutputStream(new FileOutputStream("file.txt"));
    dataInputStream = new DataInputStream(new FileInputStream("file.txt"));
  }

  @Test
  void convertStringTobytes() {
    Assertions.assertEquals("[72, 101, 108, 108, 111, 87, 111, 114, 108, 100]", Arrays.toString(protocolImp.convertStringTobytes(msg)));
  }

  @Test
  void testEncodeCONNECT_MESSAGE() throws IOException {
    String name = "wyb";
    listString = Arrays.asList(name);
    protocolImp.encode(MessageType.CONNECT_MESSAGE, listString, dataOutputStream);
    Assertions.assertEquals(Protocol.messageToIdr.get(MessageType.CONNECT_MESSAGE), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(name.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(Arrays.toString(protocolImp.convertStringTobytes(name)), Arrays.toString(dataInputStream.readAllBytes()));

  }

  @Test
  void testEncodeCONNECT_RESPONSE() throws IOException {
    String bool = "true";
    listString = Arrays.asList(bool, msg);
    protocolImp.encode(MessageType.CONNECT_RESPONSE, listString, dataOutputStream);
    Assertions.assertEquals(Protocol.messageToIdr.get(MessageType.CONNECT_RESPONSE), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(true, dataInputStream.readBoolean());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(msg.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(Arrays.toString(protocolImp.convertStringTobytes(msg)), Arrays.toString(dataInputStream.readAllBytes()));
  }

  @Test
  void testEncodeDISCONNECT_MESSAGE() throws IOException{
    String name = "wyb";
    listString = Arrays.asList(name);
    protocolImp.encode(MessageType.DISCONNECT_MESSAGE, listString, dataOutputStream);
    Assertions.assertEquals(Protocol.messageToIdr.get(MessageType.DISCONNECT_MESSAGE), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(name.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(Arrays.toString(protocolImp.convertStringTobytes(name)), Arrays.toString(dataInputStream.readAllBytes()));
  }

  @Test
  void testEncodeQUERY_USERS() throws IOException{
    String name = "wyb";
    listString = Arrays.asList(name);
    protocolImp.encode(MessageType.QUERY_USERS, listString, dataOutputStream);
    Assertions.assertEquals(Protocol.messageToIdr.get(MessageType.QUERY_USERS), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(name.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(Arrays.toString(protocolImp.convertStringTobytes(name)), Arrays.toString(dataInputStream.readAllBytes()));

  }

  @Test
  void testEncodeQUERY_RESPONSE() throws IOException {
    String name1 = "wyb";
    String name2 = "xz";
    String name3 = "bjyx";
    listString = Arrays.asList(name1, name2, name3);
    protocolImp.encode(MessageType.QUERY_RESPONSE, listString, dataOutputStream);
    Assertions.assertEquals(Protocol.messageToIdr.get(MessageType.QUERY_RESPONSE), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(listString.size(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(name1.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] name1toByte = new byte[name1.length()];
    dataInputStream.read(name1toByte);
    String user1 = new String(name1toByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(name1, user1);
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(name2.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] name2toByte = new byte[name2.length()];
    dataInputStream.read(name2toByte);
    String user2 = new String(name2toByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(name2, user2);
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(name3.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] name3toByte = new byte[name3.length()];
    dataInputStream.read(name3toByte);
    String user3 = new String(name3toByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(name3, user3);
  }

  @Test
  void testEncodeBROADCAST_MESSAGE() throws IOException{
    String userName  ="wyb";
    listString = Arrays.asList(userName, msg);
    protocolImp.encode(MessageType.BROADCAST_MESSAGE, listString, dataOutputStream);
    Assertions.assertEquals(Protocol.messageToIdr.get(MessageType.BROADCAST_MESSAGE), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(userName.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] nametoByte = new byte[userName.length()];
    dataInputStream.read(nametoByte);
    String user = new String(nametoByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(userName, user);
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(msg.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] msgtoByte = new byte[msg.length()];
    dataInputStream.read(msgtoByte);
    String newMsg = new String(msgtoByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(newMsg, msg);
  }

  @Test
  void testEncodeDIRECT_MESSAGE() throws IOException{
    String senderName = "wyb";
    String recipientName = "xz";
    listString = Arrays.asList(senderName, recipientName, msg);
    protocolImp.encode(MessageType.DIRECT_MESSAGE, listString, dataOutputStream);
    Assertions.assertEquals(Protocol.messageToIdr.get(MessageType.DIRECT_MESSAGE), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(senderName.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] senderNametoByte = new byte[senderName.length()];
    dataInputStream.read(senderNametoByte);
    String user1 = new String(senderNametoByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(senderName, user1);
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(recipientName.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] recipienttoByte = new byte[recipientName.length()];
    dataInputStream.read(recipienttoByte);
    String user2 = new String(recipienttoByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(recipientName, user2);
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(msg.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] msgtoByte = new byte[msg.length()];
    dataInputStream.read(msgtoByte);
    String newMsg = new String(msgtoByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(newMsg, msg);
  }

  @Test
  void testEncodeFAILED_MESSAGE() throws IOException{
    listString = Arrays.asList(msg);
    protocolImp.encode(MessageType.FAILED_MESSAGE, listString, dataOutputStream);
    Assertions.assertEquals(Protocol.messageToIdr.get(MessageType.FAILED_MESSAGE), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(msg.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(Arrays.toString(protocolImp.convertStringTobytes(msg)), Arrays.toString(dataInputStream.readAllBytes()));

  }

  @Test
  void testEncodeSEND_INSULT() throws IOException{
    String senderName = "wyb";
    String recipientName = "xz";
    listString = Arrays.asList(senderName, recipientName, msg);
    protocolImp.encode(MessageType.SEND_INSULT, listString, dataOutputStream);
    Assertions.assertEquals(Protocol.messageToIdr.get(MessageType.SEND_INSULT), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(senderName.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] senderNametoByte = new byte[senderName.length()];
    dataInputStream.read(senderNametoByte);
    String user1 = new String(senderNametoByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(senderName, user1);
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    Assertions.assertEquals(recipientName.length(), dataInputStream.readInt());
    Assertions.assertEquals(emptyCharacter, dataInputStream.readChar());
    byte[] recipienttoByte = new byte[recipientName.length()];
    dataInputStream.read(recipienttoByte);
    String user2 = new String(recipienttoByte, StandardCharsets.UTF_8);
    Assertions.assertEquals(recipientName, user2);

  }




}