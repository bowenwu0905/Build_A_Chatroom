package part3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xiaochong
 */
class FilePublisherTest {

  FilePublisher p1;
  CsvProcessor c1;
  String fileAddress;

  @BeforeEach
  void setUp() {
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
    Map<String,String> dayCount = new HashMap<>();
    dayCount.put("module_presentation", "0");
    dayCount.put("date","1");
    dayCount.put("total_clicks","2");
    BlockingQueue<Map<String, String>> bq = new LinkedBlockingQueue<>();
    bq.add(dayCount);
    p1.saveFileToAddress("test", bq);
    String filePath = new File("").getAbsolutePath();
    String path = filePath.concat("/" +"output_part3/test.csv");
    String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
    assertEquals("module_presentation,date,total_clicks\n"
        + "0,1,2",content);
  }

  @Test
  void getFileDestination() {
    String ans = this.c1.absolutePathChange("output_part3");
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
  void testToString() {
    assertNotNull(p1.toString());
  }
}