package sequentialSolution;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class Generator {

  private final static String fileDestination = "output";
  private Map<String, Map<String, Integer>> csvMap;
  private final static int SIZE = 2;

  public void generateFiles(Map<String, Map<String, Integer>> csvMap) throws IOException {
    for(String key: csvMap.keySet()){
      String fileName = key + ".csv";
      String finalDestination = this.fileDestination + "/" + fileName;
      FileWriter outputFile = new FileWriter(finalDestination);
      Map<String, Integer> row= csvMap.get(key);
      List<String[]> data = new ArrayList<>();
      data.add(new String[] { "Date","Total_click" });
      write(row, data, outputFile);
    }
  }
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

}

