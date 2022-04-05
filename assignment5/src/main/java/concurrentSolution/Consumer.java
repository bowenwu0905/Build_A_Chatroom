package concurrentSolution;


import static concurrentSolution.CsvProcessor.courseModule;
import static concurrentSolution.CsvProcessor.coursePresentation;
import static concurrentSolution.CsvProcessor.sumClick;
import static concurrentSolution.CsvProcessor.time;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Consumer implements Runnable{
  private BlockingQueue<Map<String,String>> buffer;
  private ConcurrentMap<String, ConcurrentMap<String,Integer>> data;

  public Consumer(BlockingQueue<Map<String,String>> buffer, ConcurrentMap<String, ConcurrentMap<String,Integer>> data ){
    this.buffer = buffer;
    this.data = data;
  }

  public void hashMapAggregator(Map<String,String> record){
    String key = record.get(courseModule)+"_"+record.get(coursePresentation);
    String date = record.get(time);
    int click = Integer.parseInt(record.get(sumClick));
    if(this.data.containsKey(key)){
      ConcurrentHashMap<String,Integer> dayCount = new ConcurrentHashMap<>();
      dayCount.put(date, click);

    }else{
      ConcurrentMap<String,Integer> dayCount = this.data.get(key);
      dayCount.put(date,dayCount.getOrDefault(date,0)+click);
      this.data.put(key,dayCount);
    }
  }


  @Override
  public void run() {
    while (true) {
      try {
        Map<String,String> record = buffer.take();
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
