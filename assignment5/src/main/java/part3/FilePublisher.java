package part3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class FilePublisher {
  private CsvProcessor processor = new CsvProcessor();
  private String fileDestination ="output_part3";
  private final static String eol = System.getProperty("line.separator");

  public static final String DATE = "date";
  public static final String TOTAL_CLICK = "total_clicks";
  public static final String MODULE_PRESENTATION = "module_presentation";

  public FilePublisher() {
    this.fileDestination = this.processor.absolutePathChange(fileDestination);
    new File(this.fileDestination).mkdirs();

  }

  public Set<String> fileNameGenerator(String courseFilePath) {
    String line;
    Set<String> fileName= new HashSet<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(this.processor.absolutePathChange(courseFilePath)));
      String[] fieldList = br.readLine().split(CsvProcessor.csvSplit);
      while ((line = br.readLine()) != null) {
        Map<String,String> record = this.processor.csvToHashMap(line, fieldList);
        fileName.add(record.get(CsvProcessor.courseModule)+"_"+record.get(
            CsvProcessor.coursePresentation));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileName;
  }

  public void saveFileToAddress(String fileName, BlockingQueue<Map<String,String>> activityDays) {
    String finalDestination = this.fileDestination + "/" + fileName+".csv";
    try(FileWriter writer = new FileWriter(finalDestination)){
      writer.append(MODULE_PRESENTATION)
          .append(',')
          .append(DATE)
          .append(',')
          .append(TOTAL_CLICK)
          .append(eol);
      while (!activityDays.isEmpty()) {
        Map<String, String> map = activityDays.take();
        writer.append(map.get(MODULE_PRESENTATION))
            .append(',')
            .append(map.get(DATE))
            .append(',')
            .append(map.get(TOTAL_CLICK))
            .append(eol);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public String getFileDestination() {
    return fileDestination;
  }

  public void setFileDestination(String fileDestination) {
    this.fileDestination = fileDestination;
    this.fileDestination = this.processor.absolutePathChange(fileDestination);
    new File(this.fileDestination).mkdirs();
  }

}
