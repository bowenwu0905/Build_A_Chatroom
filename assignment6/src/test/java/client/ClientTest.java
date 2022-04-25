package client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {
  Client c1;

  @BeforeEach
  void setUp() {
    c1 = new Client();
  }

  @Test
  void getUserName() {
    assertEquals(null,c1.getUserName());
  }

  @Test
  void testEquals() {
  }

  @Test
  void testHashCode() {
  }

  @Test
  void testToString() {
  }
}