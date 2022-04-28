package part3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xiaochong
 */
class ConsumerTest {

  Consumer consumer;
  BlockingQueue<Map<String, String>> buffer;
  CountDownLatch consumerLatch;
  CountDownLatch producerLatch;
  int threshold;
  BlockingQueue<Map<String, String>> activityDays;

  @BeforeEach
  void setUp() {
    consumerLatch = new CountDownLatch(5);
    producerLatch = new CountDownLatch(1);
    threshold = 5000;
    buffer = new LinkedBlockingQueue<>();
    activityDays = new LinkedBlockingQueue<>();
    consumer = new Consumer(buffer, consumerLatch, producerLatch, activityDays, threshold);
  }

  @Test
  void getBuffer() {
    assertNotNull(consumer.getBuffer());
  }

  @Test
  void getThreshold() {
    assertEquals(consumer.getThreshold(), 5000);
  }

  @Test
  void getActivityDays() {
    assertEquals(activityDays, consumer.getActivityDays());
  }

  @Test
  void setThreshold() {
    consumer.setThreshold(100);
    assertEquals(consumer.getThreshold(), 100);
  }

  @Test
  void testEquals(){
    assertTrue(consumer.equals(consumer));
  }

  @Test
  void testEquals1(){
    assertFalse(consumer.equals(1));
  }

  @Test
  void testEquals2(){
    assertFalse(consumer.equals(null));
  }

  @Test
  void test3(){
    Consumer c2 = new Consumer(buffer,consumerLatch,producerLatch, activityDays, threshold);
    assertTrue(consumer.equals(c2));
  }

  @Test
  void testHashCode() {
    Consumer c2 = new Consumer(buffer,consumerLatch,producerLatch, activityDays, threshold);
    assertTrue(consumer.hashCode() == c2.hashCode());
  }

  @Test
  void testToString() {
    assertNotNull(consumer.toString());
  }
}