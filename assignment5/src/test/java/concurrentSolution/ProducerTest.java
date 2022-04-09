package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProducerTest {
  BlockingQueue<Map<String,String>> buffer;
  String studentFilePath;
  Producer p1;
  CsvProcessor c1;


  @BeforeEach
  void setUp() {
    int queueLength = 5;
    buffer = new LinkedBlockingQueue<>(queueLength);
    c1 = new CsvProcessor();
    studentFilePath = c1.absolutePathChange("src/test/java/concurrentSolution/testProducer.csv");
    p1 = new Producer(buffer,studentFilePath);
  }

  @Test
  void getStudentFilePath() {
    assertEquals(c1.absolutePathChange("src/test/java/concurrentSolution/testProducer.csv"),p1.getStudentFilePath());
  }

  @Test
  void setStudentFilePath() {
    studentFilePath = c1.absolutePathChange("src/test/java/concurrentSolution/testProducer1.csv");
    p1.setStudentFilePath(studentFilePath);
    assertEquals(c1.absolutePathChange("src/test/java/concurrentSolution/testProducer1.csv"),p1.getStudentFilePath());
  }

  @Test
  void testGetBuffer(){
    assertEquals(buffer,p1.getBuffer());
  }


  @Test
  void run() throws InterruptedException {
    p1.run();
    Map<String, String> record = Stream.of(new String[][]{
        {"code_module", "test"},
        {"code_presentation", "testJ"},
        {"date", "-10"},
        {"sum_click", "4"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    assertEquals(record, p1.getBuffer().take());


  }

  @Test
  void testToString() {
    String ans ="Producer{" +
        "buffer=" + buffer +
        ", studentFilePath='" + studentFilePath + '\'' +
        ", processor=" + c1.toString() +
        '}';
    assertEquals(ans,p1.toString());
  }

  @Test
  void testEquals() {
    studentFilePath = c1.absolutePathChange("src/test/java/concurrentSolution/testProducer1.csv");
    Producer p2 = new Producer(buffer,studentFilePath);
    assertFalse(p1.equals(p2));
  }

  @Test
  void testEquals1() {
    Producer p3 = new Producer(buffer,studentFilePath);
    assertFalse(p1.equals(p3));
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
    studentFilePath = c1.absolutePathChange("src/test/java/concurrentSolution/testProducer1.csv");
    Producer p2 = new Producer(buffer,studentFilePath);
    assertFalse(p1.hashCode()==p2.hashCode());
  }

  @Test
  void testHashCode2() {
    assertFalse(p1.hashCode()!=p1.hashCode());
  }

}