package concurrentSolution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public class Producer implements Runnable{
  private BlockingQueue<Map<String,String>> buffer;
  private ConcurrentMap<String, ConcurrentMap<String,Integer>>data;
  private String studentFilePath;
  private CsvProcessor processor = new CsvProcessor();



  public Producer(BlockingQueue<Map<String,String>> buffer,ConcurrentMap<String, ConcurrentMap<String,Integer>>data, String studentFilePath) {
    this.buffer =  buffer;
    this.data = data;
    this.studentFilePath = studentFilePath;
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
        ", data=" + data +
        ", studentFilePath='" + studentFilePath + '\'' +
        ", processor=" + processor +
        '}';
  }
}
