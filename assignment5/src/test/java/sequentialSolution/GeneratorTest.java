package sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeneratorTest {
  Generator generator;
  String rootPath = new File("").getAbsolutePath();

  @BeforeEach
  void setUp() {
    generator = new Generator();
  }

  @Test
  void absolutePathChange() {
    String path = "";
    String x = generator.absolutePathChange(path);
    Assertions.assertEquals(rootPath+"/",x);
  }

  @Test
  void generateFiles() {
  }

  @Test
  void write() {
  }

  @Test
  void testEquals() {
  }

  @Test
  void testHashCode() {
  }

  @Test
  void testToString() {
  }
}