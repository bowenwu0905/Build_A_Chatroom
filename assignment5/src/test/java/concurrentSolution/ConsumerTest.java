package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
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
  Map<String,Lock> lockTable;
  CountDownLatch consumerLatch;
  CountDownLatch producerLatch;

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
    lockTable = new HashMap<>();
    lockTable.put("test_testJ",new ReentrantLock(true));
    consumerLatch = new CountDownLatch(5);
    producerLatch = new CountDownLatch(1);
    c1 = new Consumer(buffer,data,lockTable,consumerLatch,producerLatch);

  }

  @Test
  void hashMapSummarizer() throws FileNotFoundException {
    c1.hashMapSummarizer(record);
    ConcurrentMap <String,Integer> insideMap = new ConcurrentHashMap<>();
    insideMap.put("-10",4);
    ConcurrentMap<String, ConcurrentMap<String,Integer>> data = new ConcurrentHashMap<>();
    data.put("test_testJ",insideMap);
    assertEquals(data,c1.getData());
  }

  @Test
  void hashMapSummarizer2() throws FileNotFoundException {
    c1.hashMapSummarizer(record);
    c1.hashMapSummarizer(record);
    ConcurrentMap <String,Integer> insideMap = new ConcurrentHashMap<>();
    insideMap.put("-10",8);
    ConcurrentMap<String, ConcurrentMap<String,Integer>> data = new ConcurrentHashMap<>();
    data.put("test_testJ",insideMap);
    assertEquals(data,c1.getData());
  }




  @Test
  void testToString() {
    CsvProcessor processor = new CsvProcessor();
    String ans = "Consumer{" +
        "buffer=" + buffer +
        ", data=" + data +
        ", processor=" + processor +
        ", lockTable=" + lockTable +
        ", consumerLatch=" + consumerLatch +
        ", producerLatch=" + producerLatch +
        '}';
    assertEquals(ans, c1.toString());
  }

  @Test
  void testGetLockTable(){
    assertEquals(lockTable,c1.getLockTable());
  }

  @Test
  void setLockTable(){
    lockTable = new HashMap<>();
    lockTable.put("test_testJ1",new ReentrantLock(true));
    c1.setLockTable(lockTable);
    assertEquals(lockTable,c1.getLockTable());
  }

  @Test
  void testHashCode(){
    assertEquals(c1.hashCode(),c1.hashCode());
  }

  @Test
  void testHashCode1(){
    lockTable = new HashMap<>();
    lockTable.put("test_testJ1",new ReentrantLock(true));
    Consumer c2 = new Consumer(buffer,data,lockTable,consumerLatch,producerLatch);
    assertTrue(c1.hashCode()!=c2.hashCode());
  }

  @Test
  void testEquals(){
    assertTrue(c1.equals(c1));
  }

  @Test
  void testEquals1(){
    assertFalse(c1.equals(1));
  }

  @Test
  void testEquals2(){
    assertFalse(c1.equals(null));
  }

  @Test
  void test3(){
    lockTable = new HashMap<>();
    lockTable.put("test_testJ1",new ReentrantLock(true));
    Consumer c2 = new Consumer(buffer,data,lockTable,consumerLatch,producerLatch);
    assertFalse(c1.equals(c2));
  }

  @Test
  void test4(){
    consumerLatch = new CountDownLatch(8);
    Consumer c2 = new Consumer(buffer,data,lockTable,consumerLatch,producerLatch);
    assertFalse(c1.equals(c2));
  }


  @Test
  void test5(){
    CountDownLatch producerLatch1 = producerLatch;
    Consumer c2 = new Consumer(buffer,data,lockTable,consumerLatch,producerLatch1);
    assertFalse(c1.equals(c2));
  }

  @Test
  void test6(){
    Consumer c2 = new Consumer(buffer,data,lockTable,consumerLatch,producerLatch);
    assertFalse(c1.equals(c2));
  }



  @Test
  void testGetData(){
    assertEquals(data,c1.getData());
  }

  @Test
  void testSetData(){
    c1.setData(null);
    assertEquals(null,c1.getData());
  }

}