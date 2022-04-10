package concurrentSolution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * The producer class. It is for read in the raw csv data line by line. And it will push the data
 * into a fixed sized queue. If the queue is full, the buffer will stop temporarily. When the whole
 * csv file is read in, the producer will stop and the latch will count down. So the consumer can
 * know the producer is finished
 */
public class Producer implements Runnable {

  private BlockingQueue<Map<String, String>> buffer;
  private String studentFilePath;
  private CsvProcessor processor = new CsvProcessor();
  private CountDownLatch latch;
  private int bufferSize;


  /**
   * The constructor of the producer
   *
   * @param buffer          the queue for storing data passed to consumers
   * @param studentFilePath the path for the full raw csv data
   * @param latch           the producer's latch
   * @param bufferSize      the capacity of the buffer
   */
  public Producer(BlockingQueue<Map<String, String>> buffer, String studentFilePath,
      CountDownLatch latch, int bufferSize) {
    this.buffer = buffer;
    this.studentFilePath = studentFilePath;
    this.latch = latch;
    this.bufferSize = bufferSize;
  }

  /**
   * Get the full raw CSV file address
   *
   * @return the csv file path
   */
  public String getStudentFilePath() {
    return studentFilePath;
  }

  /**
   * Set the  full raw CSV file address
   *
   * @param studentFilePath the  full raw CSV file address
   */
  public void setStudentFilePath(String studentFilePath) {
    this.studentFilePath = studentFilePath;
  }

  /**
   * Get the buffer
   *
   * @return the buffer shared between producer and consumer
   */
  public BlockingQueue<Map<String, String>> getBuffer() {
    return buffer;
  }

  /**
   * The run function. IIs is for reading the data line by line and pushing the data into a fixed
   * sized queue. If the queue is full, the buffer will stop temporarily. When the whole csv file is
   * read in, the producer will stop and the latch will count down. So the consumer can know the
   * producer is finished
   */
  @Override
  public void run() {
    String line;
    try {
      BufferedReader br = new BufferedReader(
          new FileReader(this.processor.absolutePathChange(this.studentFilePath)));
      String[] fieldList = br.readLine().split(CsvProcessor.csvSplit);
      while (true) {
        //When the buffer is full, the producer will stop reading CSV
        if (this.buffer.size() < this.bufferSize) {
          if ((line = br.readLine()) != null) {
            Map<String, String> record = this.processor.csvToHashMap(line, fieldList);
            try {
              this.buffer.put(record);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } else {
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
    Producer producer = (Producer) o;
    return bufferSize == producer.bufferSize
        && Objects.equals(studentFilePath, producer.studentFilePath)
        && Objects.equals(processor, producer.processor);
  }

  /**
   * Calculate the hashcode for the object
   *
   * @return the hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(buffer, studentFilePath, processor, latch, bufferSize);
  }

  /**
   * Transfer the object to string
   *
   * @return the string
   */
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

  /**
   * Get the buffer's size
   *
   * @return an integer
   */
  public int getBufferSize() {
    return bufferSize;
  }

  /**
   * set the buffer size
   *
   * @param bufferSize an integer
   */
  public void setBufferSize(int bufferSize) {
    this.bufferSize = bufferSize;
  }


}