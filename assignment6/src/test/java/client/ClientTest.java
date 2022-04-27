package client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {
  Client client;
  Socket server;
  ServerSocket serverSocket;

  @BeforeEach
  void setUp() throws IOException {
    client = new Client();
//    serverSocket = new ServerSocket(12349);
//    server = serverSocket.accept();
  }

  @Test
  void main() throws IOException {
//    String[] input =  {"127.0.0.1","12349"};
//    String in = "zs"+ System.getProperty("line.separator");
//    InputStream ini = new ByteArrayInputStream(in.getBytes());
//    System.setIn(ini);
//    client.main(input);
  }

  @Test
  void isLogOff() {
    assertFalse(client.isLogOff());
  }

  @Test
  void setLogOff() {
    client.setLogOff(true);
    assertTrue(client.isLogOff());
  }

  @Test
  void getUserName() {
    assertEquals(null,client.getUserName());
  }

  @Test
  void setUserName() {
    client.setUserName("haha");
    assertEquals("haha",client.getUserName());
  }


  @Test
  void testEquals() {
    assertTrue(client.equals(client));
  }

  @Test
  void testEquals1() {
    assertFalse(client.equals(8));
  }

  @Test
  void testEquals2() {
    assertFalse(client.equals(null));
  }
  @Test
  void testEqual3() throws IOException {
    Client c2 = new Client();
    assertTrue(client.equals(c2));
  }

  @Test
  void testHashCode() {
    assertEquals(client.hashCode(),client.hashCode());

  }



  @Test
  void testToString() {
    String expected = "Client{}";
    assertEquals(expected,client.toString());
  }
}