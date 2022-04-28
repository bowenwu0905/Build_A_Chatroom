package part3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xiaochong
 */
class CsvProcessorTest {
  String fileDestination;
  CsvProcessor c1;
  String filePath;
  Map<String, String> record;


  @BeforeEach
  void setUp() {
    filePath = new File("").getAbsolutePath();
    fileDestination = filePath.concat("/output_part2");
    c1 = new CsvProcessor();
  }

  @Test
  void csvToHashMap() {
    String[] fieldList = { "f1","f2","f3"};
    String csvLine = "\"h1\",\"h2\",\"h3\"";
    record = Stream.of(new String[][]{
        {"f1", "h1"},
        {"f2", "h2"},
        {"f3", "h3"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    assertEquals(record,c1.csvToHashMap(csvLine,fieldList));
  }

  @Test
  void testCsvToHashMap() {
    String fileName = "abc";
    String csvLine = "\"h1\",\"h2\",\"h3\"";
    record = c1.csvToHashMap(fileName, csvLine);
    assertEquals(record.get(FilePublisher.MODULE_PRESENTATION), "abc");
  }

  @Test
  void testAbsolutePathChange(){
    String address = "data/insurance-company-members.csv";
    String path = filePath.concat("/"+address);
    assertEquals(path,c1.absolutePathChange(address));
  }
  @Test
  void testAbsolutePathChange1(){
    String path = filePath.concat("/output_part2");
    assertEquals(fileDestination,c1.absolutePathChange(path));
  }
}