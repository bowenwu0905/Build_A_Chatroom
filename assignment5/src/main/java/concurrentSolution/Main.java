package concurrentSolution;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
  private static final int queueLength = 20;
  private static final int consumerNum = 5;
  private static final int threadNum = 6;
  private static final String studetFileName = "small.csv";
  private static final String courseFileName = "courses.csv";
  private ConcurrentMap<String, ConcurrentMap<String,Integer>>data = new ConcurrentHashMap<>();
  private BlockingQueue<Map<String,String>> buffer = new LinkedBlockingQueue<>(queueLength);
  private final Lock lock = new ReentrantLock(true);


  public Main(){
  }

  public static void main(String[] args){
    Main main = new Main();
    main.run(args);
  }

  public void run(String[] args){
    String studentFilePath = args[0]+"/"+this.studetFileName;
    String courseFilePath = args[0]+"/"+this.courseFileName;

    // Generate all files based on courses list
    FilePublisher publisher = new FilePublisher();
    Set<String> fileNameSet = publisher.fileNameGenerator(courseFilePath);
    publisher.generateFiles(this.data,fileNameSet);

    // Using thread to fill the available courses
    ExecutorService executor = Executors.newFixedThreadPool(threadNum);
    executor.execute(new Producer(this.buffer, studentFilePath));
    for (int i = 0; i < consumerNum; i++) {
      executor.execute(new Consumer(this.buffer,this.data,this.lock));
    }
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
