package concurrentSolution;


import static concurrentSolution.CsvProcessor.courseModule;
import static concurrentSolution.CsvProcessor.coursePresentation;
import static concurrentSolution.CsvProcessor.sumClick;
import static concurrentSolution.CsvProcessor.time;

import java.io.File;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Consumer implements Runnable{
  private BlockingQueue<Map<String,String>> buffer;
  private ConcurrentMap<String, ConcurrentMap<String,Integer>> data;
  private CsvProcessor processor = new CsvProcessor();
  private String fileDestination ="output_part2";
//  private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private final static String eol = System.getProperty("line.separator");
  private Lock lock;
  private FilePublisher publisher = new FilePublisher();

  public Consumer(BlockingQueue<Map<String,String>> buffer, ConcurrentMap<String, ConcurrentMap<String,Integer>> data, Lock lock ){
    this.buffer = buffer;
    this.data = data;
    this.lock = lock;
    this.fileDestination = this.processor.absolutePathChange(fileDestination);
    new File(this.fileDestination).mkdirs();
  }

  public void hashMapSummarizer(Map<String,String> record){

    String key = record.get(courseModule)+"_"+record.get(coursePresentation);
    String date = record.get(time);
    ConcurrentMap<String,Integer> dayCount;
    int click = Integer.parseInt(record.get(sumClick));
    lock.lock();
    //Update the concurrent Hashmap
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
//      readWriteLock.writeLock().lock();
//      try {
        publisher.saveFileToAddress(key, dayCount);
//      } finally {
//        readWriteLock.writeLock().unlock();
//      }
    } catch (Exception e) {
      // handle the exception
    } finally {
      lock.unlock();
    }
  }


  @Override
  public void run() {
    while (!this.buffer.isEmpty()) {
      try {
          Map<String, String> record = buffer.take();
          hashMapSummarizer(record);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String toString() {
    return "Consumer{" +
        "buffer=" + buffer +
        ", data=" + data +
        '}';
  }
}
