package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {

  Main m1;

  @BeforeEach
  void setUp() {
    m1 = new Main();
  }

  @Test
  void testToString() {
    Assertions.assertEquals("Main{}", m1.toString());
  }
}