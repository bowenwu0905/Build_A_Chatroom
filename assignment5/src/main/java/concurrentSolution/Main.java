package concurrentSolution;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
  private static final int queueLength = 5;
  private ConcurrentMap<String, ConcurrentMap<String,Integer>>data = new ConcurrentHashMap<>();
  private BlockingQueue<Map<String,String>> buffer = new LinkedBlockingQueue<>(queueLength);
  private final Lock lock = new ReentrantLock(true);
  private static final int consumerNum = 1;
  private static final int threadNum = 6;

  public Main(){
  }

  public static void main(String[] args){
    Main main = new Main();

    main.run(args);
  }

  public void run(String[] args){
    String studentFilePath = args[0];
    String courseFilePath = args[1];
    Publisher publisher = new Publisher(courseFilePath);
    publisher.setFileName();
    publisher.generateFiles(this.data);

    ExecutorService executor = Executors.newFixedThreadPool(threadNum);
    executor.execute(new Producer(this.buffer, this.data, studentFilePath));
    for (int i = 0; i < consumerNum; i++) {
      executor.execute(new Consumer(this.buffer,this.data,this.lock));
    }
//    Thread p1  =  new Thread(new Producer(this.buffer, this.data, studentFilePath));
//    Thread c1 = new Thread(new Consumer(this.buffer,this.data));
//    Thread c2 = new Thread(new Consumer(this.buffer,this.data));
//    Thread c3 = new Thread(new Consumer(this.buffer,this.data));
//
//    p1.start();
//    c1.start();
//    c2.start();
//    c3.start();


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
