package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsumerTest {
  Lock lock;
  BlockingQueue<Map<String,String>> buffer;
  ConcurrentMap<String, ConcurrentMap<String,Integer>> data;
  Consumer c1;
  Map<String, String> record;

  @BeforeEach
  void setUp() throws InterruptedException {
    lock = new ReentrantLock(true);
    int queueLength = 5;
    buffer = new LinkedBlockingQueue<>(queueLength);
    record = Stream.of(new String[][]{
        {"code_module", "test"},
        {"code_presentation", "testJ"},
        {"date", "-10"},
        {"sum_click", "4"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    buffer.put(record);
    data = new ConcurrentHashMap<>();
    c1 = new Consumer(buffer,data,lock);

  }

  @Test
  void hashMapSummarizer() throws FileNotFoundException {
    c1.hashMapSummarizer(record);
    String filePath = new File("").getAbsolutePath();
    String path = filePath.concat("/" +"output_part2/test_testJ.csv");
    String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
    assertEquals("Date,Total_click\n"
        + "-10,4",content);
  }

//  @Test
//  void run() throws FileNotFoundException {
//    c1.run();
//    String filePath = new File("").getAbsolutePath();
//    String path = filePath.concat("/" +"output_part2/test_testJ.csv");
//    String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
//    assertEquals("Date,Total_click\n"
//        + "h1,1\n"
//        + "h2,2",content);
//  }



  @Test
  void testToString() {
    String ans = "Consumer{" +
        "buffer=" + buffer +
        ", data=" + data +
        '}';
    assertEquals(ans, c1.toString());
  }
}