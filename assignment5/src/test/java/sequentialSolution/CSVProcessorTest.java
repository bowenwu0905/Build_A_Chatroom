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

  private final static String courseName = "courses.csv";
  private final static String studentVle = "studentVle_testSize.csv";
  String[] inputArgs;
  String courseFilePath;
  String studentFilePath;
  CSVProcessor c1;
  Map<String, Map<String, Integer>> res;
  String rootPath = new File("").getAbsolutePath();

  @BeforeEach
  void setUp() {
    inputArgs = new String[1];
    inputArgs[0] = "src/test/java/sequentialSolution";
    courseFilePath = inputArgs[0]+"/"+ courseName;
    studentFilePath = inputArgs[0] + "/" + studentVle;
    c1 = new CSVProcessor(courseFilePath, studentFilePath, inputArgs);


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
    Assertions.assertEquals("{FFF_2014J={1=1}, CCC_2014J={1=1}, FFF_2013J={1=1}, EEE_2014B={1=1}, BBB_2013B={5=2}, BBB_2014B={1=1}, CCC_2014B={1=1}, EEE_2014J={1=1}, BBB_2013J={2=2}, BBB_2014J={12=3}, GGG_2014J={1=1}, GGG_2013J={1=1}, EEE_2013J={1=1}, GGG_2014B={1=1}, DDD_2014J={1=1}, DDD_2013J={1=1}, FFF_2014B={1=1}, AAA_2013J={10=1}, FFF_2013B={1=1}, AAA_2014J={10=5}, DDD_2014B={1=1}, DDD_2013B={1=1}}", c1.getRes().toString());
  }

  @Test
  void readCourse() throws CsvValidationException, IOException {
    c1.readCourse(courseFilePath);
    Set<String> expected = new HashSet<>(Arrays.asList("FFF_2014J, CCC_2014J, FFF_2013J, EEE_2014B, BBB_2013B, BBB_2014B, CCC_2014B, EEE_2014J, BBB_2013J, BBB_2014J, GGG_2014J, GGG_2013J, EEE_2013J, GGG_2014B, DDD_2014J, DDD_2013J, FFF_2014B, AAA_2013J, FFF_2013B, AAA_2014J, DDD_2014B, DDD_2013B"));
    Assertions.assertEquals(expected.toString(), c1.getRes().keySet().toString());

  }

  @Test
  void countCourse() throws CsvValidationException, IOException {
    c1.readCourse(courseFilePath);
    c1.countCourse(studentFilePath);
    Assertions.assertEquals("[{1=1}, {1=1}, {1=1}, {1=1}, {5=2}, {1=1}, {1=1}, {1=1}, {2=2}, {12=3}, {1=1}, {1=1}, {1=1}, {1=1}, {1=1}, {1=1}, {1=1}, {10=1}, {1=1}, {10=5}, {1=1}, {1=1}]", c1.getRes().values().toString());

  }

  @Test
  void testEquals() {
    CSVProcessor c2 = new CSVProcessor(courseFilePath, studentFilePath, inputArgs);
    Assertions.assertTrue(c1.equals(c2));
  }

  @Test
  void testHashCode() {
    CSVProcessor c2 = new CSVProcessor(courseFilePath, studentFilePath, inputArgs);
    Assertions.assertEquals(c1.hashCode(), c2.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("CSVProcessor{courseFilePath='src/test/java/sequentialSolution/courses.csv', studentFilePath='src/test/java/sequentialSolution/studentVle_testSize.csv', inputArgs=[src/test/java/sequentialSolution], res={}}", c1.toString());
  }
}