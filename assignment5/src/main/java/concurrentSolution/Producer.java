package concurrentSolution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public class Producer implements Runnable{
  private BlockingQueue<Map<String,String>> buffer;
  private String studentFilePath;
  private CsvProcessor processor = new CsvProcessor();



  public Producer(BlockingQueue<Map<String,String>> buffer, String studentFilePath) {
    this.buffer =  buffer;
    this.studentFilePath = studentFilePath;
  }

  public String getStudentFilePath() {
    return studentFilePath;
  }

  public void setStudentFilePath(String studentFilePath) {
    this.studentFilePath = studentFilePath;
  }

  public CsvProcessor getProcessor() {
    return processor;
  }

  public void setProcessor(CsvProcessor processor) {
    this.processor = processor;
  }

  @Override
  public void run() {
    String line;
    try {
      BufferedReader br = new BufferedReader(new FileReader(this.processor.absolutePathChange(this.studentFilePath)));
      String[] fieldList = br.readLine().split(this.processor.csvSplit);
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
  public String toString() {
    return "Producer{" +
        "buffer=" + buffer +
        ", studentFilePath='" + studentFilePath + '\'' +
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
        studentFilePath, producer.studentFilePath) && Objects.equals(processor,
        producer.processor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buffer, studentFilePath, processor);
  }
}
