package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVProcessorTest {
  String[] inputArgs;
  CSVProcessor c1;
  Map<String, Map<String, Integer>> res;
  String rootPath = new File("").getAbsolutePath();
  String fileAddress;

  @BeforeEach
  void setUp() {
    c1 = new CSVProcessor(inputArgs);
    fileAddress = c1.absolutePathChange("src/test/java/sequentialSolution/testCourses.csv");
    inputArgs
  }

  @Test
  void getRes() {
    Assertions.assertEquals("{}", c1.getRes().toString());
  }

  @Test
  void setRes() {
    Map<String, Map<String, Integer>> map = new HashMap<>();
    Map<String, Integer> map1 = new HashMap<>();
    map1.put("b", 2);
    map.put("hi", map1);
    c1.setRes(map);
    Assertions.assertEquals(map, c1.getRes());
  }

  @Test
  void absolutePathChange() {
    String path = "";
    String x = c1.absolutePathChange(path);
    Assertions.assertEquals(rootPath+"/",x);
  }

  @Test
  void process() {
  }

  @Test
  void readCourse() {

  }

  @Test
  void countCourse() {

  }

  @Test
  void testEquals() {
    CSVProcessor c2 = new CSVProcessor(inputArgs);
    Assertions.assertTrue(c1.equals(c2));
  }

  @Test
  void testHashCode() {
    CSVProcessor c2 = new CSVProcessor(inputArgs);
    Assertions.assertEquals(c1.hashCode(), c2.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("CSVProcessor{inputArgs=null, res={}}", c1.toString());
  }
}