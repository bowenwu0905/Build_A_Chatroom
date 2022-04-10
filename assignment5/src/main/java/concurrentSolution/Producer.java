package concurrentSolution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

public class Producer implements Runnable{
  private BlockingQueue<Map<String,String>> buffer;
  private String studentFilePath;
  private CsvProcessor processor = new CsvProcessor();
  private CountDownLatch latch;
  private int bufferSize;


  public Producer(BlockingQueue<Map<String,String>> buffer, String studentFilePath, CountDownLatch latch, int bufferSize) {
    this.buffer =  buffer;
    this.studentFilePath = studentFilePath;
    this.latch = latch;
    this.bufferSize = bufferSize;
  }

  public String getStudentFilePath() {
    return studentFilePath;
  }

  public void setStudentFilePath(String studentFilePath) {
    this.studentFilePath = studentFilePath;
  }

  public BlockingQueue<Map<String, String>> getBuffer() {
    return buffer;
  }

  @Override
  public void run() {
    String line;
    try {
      BufferedReader br = new BufferedReader(new FileReader(this.processor.absolutePathChange(this.studentFilePath)));
      String[] fieldList = br.readLine().split(CsvProcessor.csvSplit);
      while (true) {
        //When the buffer is full, the producer will stop reading CSV
        if( this.buffer.size()<this.bufferSize){
          if((line = br.readLine()) != null){
            Map<String, String> record = this.processor.csvToHashMap(line, fieldList);
            try {
              this.buffer.put(record);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }else{
            //Break when there is no file, and producer exit
            break;
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    latch.countDown();
//    System.out.println("------from produce--------------");
//    System.out.println(latch.getCount());
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
    return bufferSize == producer.bufferSize && Objects.equals(buffer, producer.buffer)
        && Objects.equals(studentFilePath, producer.studentFilePath)
        && Objects.equals(processor, producer.processor) && Objects.equals(latch,
        producer.latch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buffer, studentFilePath, processor, latch, bufferSize);
  }

  @Override
  public String toString() {
    return "Producer{" +
        "buffer=" + buffer +
        ", studentFilePath='" + studentFilePath + '\'' +
        ", processor=" + processor +
        ", latch=" + latch +
        ", bufferSize=" + bufferSize +
        '}';
  }

  public int getBufferSize() {
    return bufferSize;
  }

  public void setBufferSize(int bufferSize) {
    this.bufferSize = bufferSize;
  }
}
