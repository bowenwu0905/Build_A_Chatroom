package sequentialSolution;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Generator {

  private Map<String, Map<String, Integer>> csvMap;
  private String path;
  private final static int SIZE = 2;
  private final static String WORD = ".csv";

  public Generator(String path) {
    this.path = path;
  }


  public void generate(Map<String, Map<Integer, Integer>> csvMap, String outputPath) throws IOException {
    File dir = new File(outputPath);
    dir.mkdir();
    for(String key: csvMap.keySet()) {
      Map<Integer, Integer> row = csvMap.get(key);
      List<String[]> data = new ArrayList<>();
      data.add(new String[]{"Date", "Count"});
      String name = key + WORD;
      File file = new File(dir, name);
      FileWriter outputFile = new FileWriter(file);
      write(row, data, outputFile);
    }
  }

  public void write(Map<Integer, Integer> row, List<String[]> data,
      FileWriter outputFile) throws IOException {
    System.out.println("write");
    CSVWriter writer = new CSVWriter(outputFile);

    for(Integer date: row.keySet()){
      String[] cell = new String[SIZE];
      cell[0] = String.valueOf(date);
      cell[1] = String.valueOf(row.get(date));
      data.add(cell);
    }
    writer.writeAll(data);
    writer.close();
  }

}
