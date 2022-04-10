package concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import part3.Publisher;

class FilePublisherTest {
  FilePublisher p1;
  CsvProcessor c1;
  String fileAddress;


  @BeforeEach
  void setup(){
    p1 = new FilePublisher();
    c1 = new CsvProcessor();
    fileAddress = c1.absolutePathChange("src/test/java/concurrentSolution/testCourses.csv");
  }


  @Test
  void fileNameGenerator() {
    Set<String> expected = new HashSet<>(Arrays.asList("AAA_2013J", "AAA_2014J","BBB_2013J"));
    assertEquals(expected,p1.fileNameGenerator(fileAddress));
  }

  @Test
  void saveFileToAddress() throws FileNotFoundException {
    Map<String,Integer> dayCount = new HashMap<>();
    dayCount.put("h1",1);
    dayCount.put("h2",2);
    p1.saveFileToAddress("test",dayCount);
    String filePath = new File("").getAbsolutePath();
    String path = filePath.concat("/" +"output_part2/test.csv");
    String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
    assertEquals("Date,Total_click\n"
        + "h1,1\n"
        + "h2,2",content);
  }

  @Test
  void generateFiles() throws FileNotFoundException {
    Set<String> fileNameSet = new HashSet<>(Arrays.asList("testGenerateFiles"));
    ConcurrentMap<String, ConcurrentMap<String,Integer>> data  = new ConcurrentHashMap<>();
    p1.generateFiles(data,fileNameSet);
    String filePath = new File("").getAbsolutePath();
    String path = filePath.concat("/" +"output_part2/testGenerateFiles.csv");
    String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
    assertEquals("Date,Total_click",content);
  }


  @Test
  void getFileDestination() {
    String ans = this.c1.absolutePathChange("output_part2");
    assertEquals(ans,p1.getFileDestination());
  }

  @Test
  void setFileDestination() {
    this.p1.setFileDestination("output1_part2");
    String ans = this.c1.absolutePathChange("output1_part2");
    assertEquals(ans,p1.getFileDestination());
  }

  @Test
  void testEquals() {
    FilePublisher p2 = new FilePublisher();
    p2.setFileDestination("output1_part2");
    assertFalse(p1.equals(p2));
  }

  @Test
  void testEquals1() {
    assertTrue(p1.equals(p1));
  }

  @Test
  void testEquals2() {
    assertFalse(p1.equals(2));
  }

  @Test
  void testEquals3() {
    assertFalse(p1.equals(null));
  }


  @Test
  void testHashCode() {
    FilePublisher p2 = new FilePublisher();
    p2.setFileDestination("output1_part2");
    assertFalse(p1.hashCode() == p2.hashCode());
  }

  @Test
  void testHashCode2() {

    assertFalse(p1.hashCode() != p1.hashCode());
  }


  @Test
  void testToString() {
    String processor = c1.toString();
    String fileDestination = c1.absolutePathChange("output_part2");
    String ans = "Publisher{" +
        "processor=" + processor +
        ", fileDestination='" + fileDestination + '\'' +
        '}';
    assertEquals(ans,p1.toString());
  }

  @Test
  void testLockMapGenerator(){
    Set<String> expected = new HashSet<>(Arrays.asList("AAA_2013J"));
    Map<String, Lock> dic = new HashMap<>();
    dic.put("AAA_2013J",new ReentrantLock(true));
    assertEquals(dic.keySet(),p1.lockMapGenerator(expected).keySet());
  }
}