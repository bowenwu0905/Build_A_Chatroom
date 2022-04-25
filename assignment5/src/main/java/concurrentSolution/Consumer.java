package concurrentSolution;


import static concurrentSolution.CsvProcessor.courseModule;
import static concurrentSolution.CsvProcessor.coursePresentation;
import static concurrentSolution.CsvProcessor.sumClick;
import static concurrentSolution.CsvProcessor.time;


import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;


/**
 * The consumer class. It is for updtaing the hashmap. It will keep running until the producer
 * terminates and the buffer is empty
 */
public class  Consumer implements Runnable {

  private BlockingQueue<Map<String, String>> buffer;
  private ConcurrentMap<String, ConcurrentMap<String, Integer>> data;
  private CsvProcessor processor = new CsvProcessor();
  private Map<String, Lock> lockTable;
  private CountDownLatch consumerLatch;
  private CountDownLatch producerLatch;

  /**
   * The consumer class
   *
   * @param buffer        the buffer for transferring information
   * @param data          the hashmap for storing all summaried class information
   * @param lockTable     the lookup table for matching class with its locks
   * @param consumerLatch the latch from consumers
   * @param producerLatch the latch from producers
   */
  public Consumer(BlockingQueue<Map<String, String>> buffer,
      ConcurrentMap<String, ConcurrentMap<String, Integer>> data, Map<String, Lock> lockTable,
      CountDownLatch consumerLatch, CountDownLatch producerLatch) {
    this.buffer = buffer;
    this.data = data;
    this.lockTable = lockTable;
    this.consumerLatch = consumerLatch;
    this.producerLatch = producerLatch;
  }

  /**
   * update the concurrent hashmap summaried class map based on the record. The file will be locked
   * if several threads access the same course
   *
   * @param record the record from the buffer
   */
  public void hashMapSummarizer(Map<String, String> record) {

    String key = record.get(courseModule) + "_" + record.get(coursePresentation);
    String date = record.get(time);
    ConcurrentMap<String, Integer> dayCount;

    //Find the related lock based on file name
    Lock fileLock = lockTable.get(key);
    int click = Integer.parseInt(record.get(sumClick));
    fileLock.lock();
    try {
      if (!this.data.containsKey(key)) {
        dayCount = new ConcurrentHashMap<>();
        dayCount.put(date, click);
        this.data.put(key, dayCount);
      } else {
        dayCount = this.data.get(key);
        dayCount.put(date, dayCount.getOrDefault(date, 0) + click);
        this.data.put(key, dayCount);

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    fileLock.unlock();
  }


  /**
   * It will keep running until the producer terminates and * the buffer is empty. The latch will
   * count down when the consumer finishes
   */
  @Override
  public void run() {
    synchronized (this.buffer) {
      //Keep processing while producer still work or buffer isn't empty
      while ((producerLatch.getCount() > 0) || (!this.buffer.isEmpty())) {
        if (!this.buffer.isEmpty()) {
          Map<String, String> record = null;
          try {
            record = buffer.take();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          hashMapSummarizer(record);
        }
      }
    }
    consumerLatch.countDown();
//    System.out.println("------from consumer--------------");
//    System.out.println(data.toString());
//    System.out.println("here"+latch.getCount());
  }

  /**
   * Transfer the object to string
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Consumer{" +
        "buffer=" + buffer +
        ", data=" + data +
        ", processor=" + processor +
        ", lockTable=" + lockTable +
        ", consumerLatch=" + consumerLatch +
        ", producerLatch=" + producerLatch +
        '}';
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
    Consumer consumer = (Consumer) o;
    return Objects.equals(data,
        consumer.data) && Objects.equals(processor, consumer.processor);
  }

  /**
   * Calculate the hashcode for the object
   *
   * @return the hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(buffer, data, processor, lockTable, consumerLatch,
        producerLatch);
  }

  /**
   * get the lock look up table
   *
   * @return the lock look up table
   */
  public Map<String, Lock> getLockTable() {
    return lockTable;
  }

  /**
   * set the lock look up table
   *
   * @param lockTable the lock look up table
   */
  public void setLockTable(Map<String, Lock> lockTable) {
    this.lockTable = lockTable;
  }

  /**
   * Get the concurrent hashmap for summarized class data
   *
   * @return the concurrent hashmap for summarized class data
   */
  public ConcurrentMap<String, ConcurrentMap<String, Integer>> getData() {
    return data;
  }

  /**
   * set the concurrent hashmap for summarized class data
   *
   * @param data the concurrent hashmap for summarized class data
   */
  public void setData(
      ConcurrentMap<String, ConcurrentMap<String, Integer>> data) {
    this.data = data;
  }
}
