package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xiaochong
 */
class MainTest {
  private Main main;
  @BeforeEach
  void setUp() {
    main = new Main();
  }

//  @Test
//  void main() {
//    main();
//  }
//
//  @Test
//  void run() {
//    run();
//  }

  @Test
  void testToString() {
    Main main1 = new Main();
    Assertions.assertEquals(main1.toString(), main.toString());
    Assertions.assertNotEquals(main, main1);
  }
}