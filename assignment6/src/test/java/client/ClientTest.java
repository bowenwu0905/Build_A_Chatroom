package client;

import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {

  Client client;


  @BeforeEach
  void setUp() {
    client = new Client();
  }

  @Test
  void main() {

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
    assertEquals(null, client.getUserName());
  }

  @Test
  void setUserName() {
    client.setUserName("haha");
    assertEquals("haha", client.getUserName());
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
    assertEquals(client.hashCode(), client.hashCode());

  }


  @Test
  void testToString() {
    String expected = "Client{}";
    assertEquals(expected, client.toString());
  }
}