package concurrentSolution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class FilePublisher {
  private CsvProcessor processor = new CsvProcessor();
  private String fileDestination ="output";
  private final static String eol = System.getProperty("line.separator");

  public FilePublisher() {
    this.fileDestination = this.processor.absolutePathChange(fileDestination);
    new File(this.fileDestination).mkdirs();

  }


  public Set<String> fileNameGenerator(String courseFilePath) {
    String line;
    Set<String> fileName= new HashSet<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(this.processor.absolutePathChange(courseFilePath)));
      String[] fieldList = br.readLine().split(this.processor.csvSplit);
      while ((line = br.readLine()) != null) {
        Map<String,String> record = this.processor.csvToHashMap(line, fieldList);
        fileName.add(record.get(this.processor.courseModule)+"_"+record.get(this.processor.coursePresentation));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileName;
  }

  public void saveFileToAddress(String fileName, Map<String,Integer> dayCount) {
      String finalDestination = this.fileDestination + "/" + fileName+".csv";
      try(FileWriter writer = new FileWriter(finalDestination)){
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

  public void generateFiles(ConcurrentMap<String, ConcurrentMap<String,Integer>>data,Set<String> fileNameSet){
    Iterator<String> namesIterator = fileNameSet.iterator();
    while(namesIterator.hasNext()) {
      String fileName = namesIterator.next();
      if(data.containsKey(fileName)){
        saveFileToAddress(fileName,data.get(fileName));
      }else{
        saveFileToAddress(fileName,new HashMap<>());
      }
    }

  }


  public CsvProcessor getProcessor() {
    return processor;
  }

  public void setProcessor(CsvProcessor processor) {
    this.processor = processor;
  }

  public String getFileDestination() {
    return fileDestination;
  }

  public void setFileDestination(String fileDestination) {
    this.fileDestination = fileDestination;
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(processor, fileDestination);
  }

  @Override
  public String toString() {
    return "Publisher{" +
        "processor=" + processor +
        ", fileDestination='" + fileDestination + '\'' +
        '}';
  }
}
