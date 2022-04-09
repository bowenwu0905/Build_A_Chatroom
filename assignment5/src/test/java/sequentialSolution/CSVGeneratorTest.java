package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVGeneratorTest {
  CSVGenerator generator;
  String[] inputArgs;
  CSVProcessor c1;
  String rootPath = new File("").getAbsolutePath();

  @BeforeEach
  void setUp() {
    generator = new CSVGenerator();
    inputArgs = new String[1];
    c1 = new CSVProcessor(inputArgs);
    inputArgs[0] = "src/test/java/sequentialSolution";
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
  void testHashCode() {
    CSVGenerator generator1 = new CSVGenerator();
    Assertions.assertEquals(generator1.hashCode(), generator.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("CSVGenerator{fileDestination='output_part1', csvMap=null}", generator.toString());
  }
}