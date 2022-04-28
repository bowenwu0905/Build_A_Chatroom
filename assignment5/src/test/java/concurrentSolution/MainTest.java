package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
  Main m1;
  ConcurrentMap<String, ConcurrentMap<String,Integer>>data;
  BlockingQueue<Map<String,String>> buffer;

  @BeforeEach
  void setUp() {
    m1 = new Main();
    buffer = new LinkedBlockingQueue<>(5);
    data = new ConcurrentHashMap<>();
  }

  @Test
  void testToString() {
    String ans = "Main{" +
        "data=" + data +
        ", buffer=" + buffer +
        '}';
    assertEquals(ans,m1.toString());
  }
}