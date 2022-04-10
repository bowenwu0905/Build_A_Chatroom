package concurrentSolution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The file publisher class. It is for generating the text file based on the hashmap. Also, it
 * generating the files' names and lookup table for lock
 */
public class FilePublisher {

  private CsvProcessor processor = new CsvProcessor();
  private String fileDestination = "output_part2";
  private final static String eol = System.getProperty("line.separator");

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
    Set<String> fileName = new HashSet<>();
    try {
      BufferedReader br = new BufferedReader(
          new FileReader(this.processor.absolutePathChange(courseFilePath)));
      String[] fieldList = br.readLine().split(CsvProcessor.csvSplit);
      while ((line = br.readLine()) != null) {
        Map<String, String> record = this.processor.csvToHashMap(line, fieldList);
        fileName.add(record.get(CsvProcessor.courseModule) + "_" + record.get(
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
   * @param dayCount the hashmap has the day, and sum_clicks for a specific courses
   */
  public void saveFileToAddress(String fileName, Map<String, Integer> dayCount) {
    String finalDestination = this.fileDestination + "/" + fileName + ".csv";
    try (FileWriter writer = new FileWriter(finalDestination)) {
      writer.append("Date")
          .append(',')
          .append("Total_click")
          .append(eol);
      for (Map.Entry<String, Integer> entry : dayCount.entrySet()) {
        writer.append(entry.getKey())
            .append(',')
            .append(entry.getValue().toString())
            .append(eol);
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Generate files based on the map of class and dates. If the filename cannot be found in the map,
   * an empty csv file will be generated
   *
   * @param data        the Concurrent map for storing all summarized course information
   * @param fileNameSet the set for storing all filenames
   */
  public void generateFiles(ConcurrentMap<String, ConcurrentMap<String, Integer>> data,
      Set<String> fileNameSet) {
    for (String fileName : fileNameSet) {
      if (data.containsKey(fileName)) {
        saveFileToAddress(fileName, data.get(fileName));
      } else {
        saveFileToAddress(fileName, new HashMap<>());
      }
    }
  }

  /**
   * Generate the lock look up tables. The key is class name , the value is the lock for that
   * courses
   *
   * @param fileNameSet he set for storing all filenames
   * @return a hashmap with class name as key, the lock as values
   */
  public Map<String, Lock> lockMapGenerator(Set<String> fileNameSet) {
    Map<String, Lock> lockTable = new HashMap<>();
    for (String fileName : fileNameSet) {
      lockTable.put(fileName, new ReentrantLock(true));
    }
    return lockTable;
  }


  /**
   * get the output destination
   *
   * @return the output destination
   */
  public String getFileDestination() {
    return fileDestination;
  }

  public void setFileDestination(String fileDestination) {
    this.fileDestination = fileDestination;
    this.fileDestination = this.processor.absolutePathChange(fileDestination);
    new File(this.fileDestination).mkdirs();
  }

  /**
   * Check if two objects are equal
   *
   * @param o the other object
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FilePublisher publisher = (FilePublisher) o;
    return Objects.equals(processor, publisher.processor) && Objects.equals(
        fileDestination, publisher.fileDestination);
  }

  /**
   * Calculate the hashcode for the object
   *
   * @return the hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(processor, fileDestination);
  }

  /**
   * Transfer the object to string
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Publisher{" +
        "processor=" + processor +
        ", fileDestination='" + fileDestination + '\'' +
        '}';
  }
}
