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
/**
 * The file publisher class. It is for generating the text file based on the hashmap. Also, it
 * generating the files' names and lookup table for lock
 */
public class FilePublisher {
  private CsvProcessor processor = new CsvProcessor();
  private String fileDestination ="output_part3";
  private final static String eol = System.getProperty("line.separator");

  public static final String DATE = "date";
  public static final String TOTAL_CLICK = "total_clicks";
  public static final String MODULE_PRESENTATION = "module_presentation";
  /**
   * The constructor
   */
  public FilePublisher() {
    this.fileDestination = this.processor.absolutePathChange(fileDestination);
    new File(this.fileDestination).mkdirs();

  }
  /**
   * Generating the file name from the courses.csv
   *
   * @param courseFilePath the path for courses.csv
   * @return a hashset for all filenames
   */
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
  /**
   * Write the hashmap to a csv file
   *
   * @param fileName the filename based course information
   * @param activityDays the queue have the values larger than threshold
   */
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
  /**
   * get the output destination
   *
   * @return the output destination
   */
  public String getFileDestination() {
    return fileDestination;
  }

  /**
   * set file destination
   * @param fileDestination
   */
  public void setFileDestination(String fileDestination) {
    this.fileDestination = fileDestination;
    this.fileDestination = this.processor.absolutePathChange(fileDestination);
    new File(this.fileDestination).mkdirs();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FilePublisher)) {
      return false;
    }
    FilePublisher that = (FilePublisher) o;
    return Objects.equals(processor, that.processor) && Objects.equals(
        getFileDestination(), that.getFileDestination());
  }

  @Override
  public int hashCode() {
    return Objects.hash(processor, getFileDestination());
  }

  @Override
  public String toString() {
    return "FilePublisher{" +
        "processor=" + processor +
        ", fileDestination='" + fileDestination + '\'' +
        '}';
  }
}
