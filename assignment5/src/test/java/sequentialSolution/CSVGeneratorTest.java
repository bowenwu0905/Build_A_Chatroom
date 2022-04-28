package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVGeneratorTest {
  CSVGenerator generator;
  String rootPath = new File("").getAbsolutePath();

  @BeforeEach
  void setUp() {
    generator = new CSVGenerator();
  }

  @Test
  void absolutePathChange() {
    String path = "";
    String x = generator.absolutePathChange(path);
    Assertions.assertEquals(rootPath+"/",x);
  }

  @Test
  void getFileDestination() {
    Assertions.assertEquals("output_part1", generator.getFileDestination());
  }

  @Test
  void setFileDestination() {
    generator.setFileDestination("output_part1/test");
    Assertions.assertEquals("output_part1/test", generator.getFileDestination());
  }

  @Test
  void generateCSVFiles() throws IOException {
    Map<String, Map<String, Integer>> map = new HashMap<>();
    Map<String, Integer> map1= new HashMap<>();
    map1.put("10", 1);
    map.put("abc", map1);
    generator.setFileDestination("output_part1/test");
    generator.generateCSVFiles(map);
    String filePath = new File("").getAbsolutePath();
    String path = filePath.concat("/output_part1/test/abc.csv");
    String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
    Assertions.assertEquals("\"Date\",\"Total_click\"\n"
        + "\"10\",\"1\"", content);
  }

  @Test
  void write() {
  }

  @Test
  void testEquals() {
    CSVGenerator generator1 = new CSVGenerator();
    Assertions.assertTrue(generator.equals(generator1));
  }

  @Test
  void testEquals1() {
    assertFalse(generator.equals(null));
  }

  @Test
  void testEquals2() {
    assertTrue(generator.equals(generator));
  }

  @Test
  void testHashCode() {
    CSVGenerator generator1 = new CSVGenerator();
    Assertions.assertEquals(generator1.hashCode(), generator.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("CSVGenerator{fileDestination='output_part1', csvMap=null}", generator.toString());
  }
}