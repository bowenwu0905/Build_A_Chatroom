package part3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xiaochong
 */
class ProducerTest {

  BlockingQueue<Map<String,String>> buffer;
  String folderPath;
  Producer p1;
  CsvProcessor c1;
  CountDownLatch latch;
  private Set<String> fileNameSet;
  FilePublisher publisher;

  @BeforeEach
  void setUp() {
    buffer = new LinkedBlockingQueue<>();
    c1 = new CsvProcessor();
    publisher = new FilePublisher();
    latch = new CountDownLatch(1);
    fileNameSet = publisher.fileNameGenerator("data/courses.csv");
    folderPath = "output_part3";
    p1 = new Producer(buffer,folderPath,latch,fileNameSet);
  }

  @Test
  void getFolderPath() {
    Assertions.assertEquals(folderPath, "output_part3");
  }

  @Test
  void setFolderPath() {
    p1.setFolderPath("output_part2");
    Assertions.assertEquals(p1.getFolderPath(), "output_part2");
  }

  @Test
  void getBuffer() {
    assertEquals(buffer,p1.getBuffer());
  }

  @Test
  void getFileNameSet() {
    assertEquals(fileNameSet,fileNameSet);
  }

  @Test
  void run() {
    p1.run();
    assertNotNull(p1.getBuffer());
  }

  @Test
  void testEquals() {
    Producer p2 = new Producer(buffer,folderPath,latch,fileNameSet);
    assertFalse(p1.equals(p2));
  }

  @Test
  void testEquals2() {
    assertFalse(p1.equals(null));
  }

  @Test
  void testEquals3() {
    assertTrue(p1.equals(p1));
  }

  @Test
  void testEquals4() {
    assertTrue(p1.equals(p1));
  }

  @Test
  void testHashCode() {
    Producer p2 = new Producer(buffer,folderPath,latch,fileNameSet);
    assertNotEquals(p1.hashCode(), p2.hashCode());
  }

  @Test
  void testToString() {
    assertNotNull(p1.toString());
  }
}