package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
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
  void generateCSVFiles() {
  }

  @Test
  void write() {
  }

  @Test
  void testEquals() {
    CSVGenerator generator1 = new CSVGenerator();
    Assertions.assertEquals(generator1, generator);
  }

  @Test
  void testHashCode() {
    CSVGenerator generator1 = new CSVGenerator();
    Assertions.assertEquals(generator1.hashCode(), generator.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertEquals("CSVGenerator{fileDestination='output', csvMap=null}", generator.toString());
  }
}