package concurrentSolution;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
  private static final int queueLength = 5;
  private ConcurrentMap<String, ConcurrentMap<String,Integer>>data = new ConcurrentHashMap<>();
  private BlockingQueue<Map<String,String>> buffer = new LinkedBlockingQueue<>(queueLength);
  private static final int consumerNum = 2;
  private static final int threadNum = 3;

  public Main(){
  }

  public static void main(String[] args){
    Main main = new Main();
    main.run(args);
  }

  public void run(String[] args){
    String studentFilePath = args[0];
    String courseFilePath = args[1];
    ExecutorService executor = Executors.newFixedThreadPool(threadNum);
    executor.execute(new Producer(this.buffer, this.data, studentFilePath));
    for (int i = 0; i < consumerNum; i++) {
      executor.execute(new Consumer(this.buffer,this.data));
    }


    Publisher publisher = new Publisher(courseFilePath);
    publisher.setFileName();
    publisher.generateFiles(this.data);
    executor.shutdown();
  }

  @Override
  public String toString() {
    return "Main{" +
        "data=" + data +
        ", buffer=" + buffer +
        '}';
  }
}
