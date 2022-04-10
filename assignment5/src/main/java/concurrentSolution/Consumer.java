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


public class Consumer implements Runnable{
  private BlockingQueue<Map<String,String>> buffer;
  private ConcurrentMap<String, ConcurrentMap<String,Integer>> data;
  private CsvProcessor processor = new CsvProcessor();
  private Map<String,Lock> lockTable;
  private CountDownLatch consumerLatch;
  private CountDownLatch producerLatch;

  public Consumer(BlockingQueue<Map<String,String>> buffer, ConcurrentMap<String, ConcurrentMap<String,Integer>> data,Map<String,Lock> lockTable,CountDownLatch consumerLatch, CountDownLatch producerLatch){
    this.buffer = buffer;
    this.data = data;
    this.lockTable = lockTable;
    this.consumerLatch = consumerLatch;
    this.producerLatch = producerLatch;
  }

  public void hashMapSummarizer(Map<String,String> record){

    String key = record.get(courseModule)+"_"+record.get(coursePresentation);
    String date = record.get(time);
    ConcurrentMap<String,Integer> dayCount;

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


  @Override
  public void run() {
    synchronized(this.buffer) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Consumer consumer = (Consumer) o;
    return Objects.equals(buffer, consumer.buffer) && Objects.equals(data,
        consumer.data) && Objects.equals(processor, consumer.processor)
        && Objects.equals(lockTable, consumer.lockTable) && Objects.equals(
        consumerLatch, consumer.consumerLatch) && Objects.equals(producerLatch,
        consumer.producerLatch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buffer, data, processor, lockTable, consumerLatch,
        producerLatch);
  }

  public Map<String, Lock> getLockTable() {
    return lockTable;
  }

  public void setLockTable(Map<String, Lock> lockTable) {
    this.lockTable = lockTable;
  }


}
