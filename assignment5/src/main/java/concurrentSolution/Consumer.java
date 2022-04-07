package concurrentSolution;


import static concurrentSolution.CsvProcessor.courseModule;
import static concurrentSolution.CsvProcessor.coursePresentation;
import static concurrentSolution.CsvProcessor.sumClick;
import static concurrentSolution.CsvProcessor.time;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Consumer implements Runnable{
  private BlockingQueue<Map<String,String>> buffer;
  private ConcurrentMap<String, ConcurrentMap<String,Integer>> data;
  private CsvProcessor processor = new CsvProcessor();
  private String fileDestination ="output";
  private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private final static String eol = System.getProperty("line.separator");
  private Lock lock;

  public Consumer(BlockingQueue<Map<String,String>> buffer, ConcurrentMap<String, ConcurrentMap<String,Integer>> data, Lock lock ){
    this.buffer = buffer;
    this.data = data;
    this.lock = lock;
    this.fileDestination = this.processor.absolutePathChange(fileDestination);
    new File(this.fileDestination).mkdirs();
  }

  public void hashMapAggregator(Map<String,String> record){

    String key = record.get(courseModule)+"_"+record.get(coursePresentation);
    String date = record.get(time);

      ConcurrentMap<String,Integer> dayCount;
      int click = Integer.parseInt(record.get(sumClick));
    lock.lock();
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

      readWriteLock.writeLock().lock();
      try {
        saveFileToAddress(key, dayCount);
      } finally {
        readWriteLock.writeLock().unlock();
      }

    } catch (Exception e) {
      // handle the exception
    } finally {
      lock.unlock();
    }

  }

  public void saveFileToAddress(String fileName, ConcurrentMap<String,Integer> dayCount) {
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


  @Override
  public void run() {
    while (true) {
      try {

          Map<String, String> record = buffer.take();
          hashMapAggregator(record);

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
