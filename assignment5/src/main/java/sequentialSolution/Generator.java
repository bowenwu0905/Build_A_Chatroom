package sequentialSolution;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Generator class of sequential solution
 *
 * @author bowen
 */
public class Generator {

  private String fileDestination = "output";
  private Map<String, Map<String, Integer>> csvMap;
  private final static int SIZE = 2;

  /**
   *
   * @param path, the path of the parameter taken in
   * @return absolutePath, string
   */
  public String absolutePathChange(String path) {
    File file = new File(path);
    if (file.isAbsolute()) {
      return path;
    } else {
      String filePath = new File("").getAbsolutePath();

      return filePath.concat("/" + path);
    }
  }

  /**
   *
   * @param csvMap, the csvMap generated
   * @throws IOException when certain error happens of the FileWriter
   * Generate files with the information in csvMap
   */
  public void generateFiles(Map<String, Map<String, Integer>> csvMap) throws IOException {
    this.fileDestination = absolutePathChange(fileDestination);
    new File(this.fileDestination).mkdirs();

    for(String key: csvMap.keySet()){
      String fileName = key + ".csv";
      String finalDestination = this.fileDestination + "/" + fileName;
      FileWriter outputFile = new FileWriter(finalDestination);
      Map<String, Integer> row = csvMap.get(key);
      List<String[]> data = new ArrayList<>();
      data.add(new String[] { "Date","Total_click" });
      write(row, data, outputFile);
    }
  }

  /**
   *
   * @param row, the value of the csvMap
   * @param data, the content of the file
   * @param outputFile, the file generated as the output
   * @throws IOException when certain error happens of the CSVWriter
   */
  public void write(Map<String, Integer> row, List<String[]> data, FileWriter outputFile)
      throws IOException {
    CSVWriter writer = new CSVWriter(outputFile);
    for(String date: row.keySet()){
      String[] output = new String[SIZE];
      output[0] = date;
      output[1] = String.valueOf(row.get(date));
      data.add(output);
    }
    writer.writeAll(data);
    writer.close();

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Generator)) {
      return false;
    }
    Generator generator = (Generator) o;
    return Objects.equals(fileDestination, generator.fileDestination)
        && Objects.equals(csvMap, generator.csvMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileDestination, csvMap);
  }

  @Override
  public String toString() {
    return "Generator{" +
        "fileDestination='" + fileDestination + '\'' +
        ", csvMap=" + csvMap +
        '}';
  }
}

