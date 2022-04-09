package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVProcessorTest {
  String[] inputArgs;
  CSVProcessor c1;
  Map<String, Map<String, Integer>> res;
  String rootPath = new File("").getAbsolutePath();

  @BeforeEach
  void setUp() {
    inputArgs = new String[1];
    c1 = new CSVProcessor(inputArgs);
    inputArgs[0] = "src/test/java/sequentialSolution";
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
  void process() throws CsvValidationException, IOException {
    c1.process();
    Assertions.assertEquals("{AAA_2013J={10=1}, BBB_2013J={10=1}, AAA_2014J={10=1}}", c1.getRes().toString());
  }

  @Test
  void readCourse() throws CsvValidationException, IOException {
    c1.readCourse();
    Set<String> expected = new HashSet<>(Arrays.asList("AAA_2013J", "AAA_2014J","BBB_2013J"));
    Assertions.assertEquals(expected, c1.getRes().keySet());

  }

  @Test
  void countCourse() throws CsvValidationException, IOException {
    c1.readCourse();
    c1.countCourse();
    Assertions.assertEquals("[{10=1}, {10=1}, {10=1}]", c1.getRes().values().toString());

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
    Assertions.assertEquals("CSVProcessor{inputArgs=[src/test/java/sequentialSolution], res={}}", c1.toString());
  }
}