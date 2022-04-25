package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandTest {
  Command c;
  @BeforeEach
  void setUp() {
    c = new Command();
  }

  @Test
  void testToString() {
    String expected = "Command{}";
    assertEquals(expected,c.toString());
  }
}