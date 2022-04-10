package part3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
  private BlockingQueue<Map<String,String>> buffer;
  private String folderPath;
  private CsvProcessor processor = new CsvProcessor();
  private Set<String> fileNameSet;


  public Producer(BlockingQueue<Map<String,String>> buffer, Set<String> fileNameSet, String folderPath) {
    this.buffer =  buffer;
    this.folderPath = folderPath;
    this.fileNameSet = fileNameSet;
  }

  public String getFolderPath() {
    return folderPath;
  }

  public void setFolderPath(String folderPath) {
    this.folderPath = folderPath;
  }

  public BlockingQueue<Map<String, String>> getBuffer() {
    return buffer;
  }

  public void runnable() {
    String line;
    try {
      BufferedReader br = new BufferedReader(new FileReader(this.processor.absolutePathChange(this.folderPath)));
      String[] fieldList = br.readLine().split(CsvProcessor.csvSplit);
      while ((line = br.readLine()) != null) {
        Map<String,String> record = this.processor.csvToHashMap(line, fieldList);
        try {
          this.buffer.put(record);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    String line;
    File f = new File(this.processor.absolutePathChange(folderPath));
    File[] listOfFiles = f.listFiles();
    for (int i = 0; i < listOfFiles.length; i++) {
      if (this.fileNameSet.contains(listOfFiles[i])) {
        BufferedReader br = null;
        try {
          br = new BufferedReader(new FileReader(listOfFiles[i]));
          String[] fieldList = br.readLine().split(CsvProcessor.csvSplit);
          while ((line = br.readLine()) != null) {
            Map<String,String> record = this.processor.csvToHashMap(line, fieldList);
            this.buffer.put(record);
          }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public String toString() {
    return "Producer{" +
        "buffer=" + buffer +
        ", folderPath='" + folderPath + '\'' +
        ", processor=" + processor +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Producer producer = (Producer) o;
    return Objects.equals(buffer, producer.buffer) && Objects.equals(
        folderPath, producer.folderPath) && Objects.equals(processor,
        producer.processor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buffer, folderPath, processor);
  }
}
