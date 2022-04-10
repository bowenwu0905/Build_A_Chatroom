package concurrentSolution;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;


public class Main {
  private static final int capacity = 10;
  private static final int consumerNum = 5;
  private static final int producerNum = 1;
  private static final String studetFileName = "studentVle.csv";
  private static final String courseFileName = "courses.csv";
  private ConcurrentMap<String, ConcurrentMap<String,Integer>>data = new ConcurrentHashMap<>();
  private BlockingQueue<Map<String,String>> buffer = new LinkedBlockingQueue<>(this.capacity);

  public Main(){
  }

  public static void main(String[] args) throws InterruptedException {
    Main main = new Main();
    main.run(args);
  }

  public void run(String[] args) throws InterruptedException {
    String studentFilePath = args[0]+"/"+this.studetFileName;
    String courseFilePath = args[0]+"/"+this.courseFileName;
    FilePublisher publisher = new FilePublisher();

    //Generate Lock table
    Set<String> fileNameSet = publisher.fileNameGenerator(courseFilePath);
    Map<String,Lock> lockTable = publisher.lockMapGenerator(fileNameSet);

    //Generate the producer and consumer lock
    CountDownLatch producerLatch = new CountDownLatch(producerNum);
    CountDownLatch consumerLatch = new CountDownLatch(consumerNum);
    ExecutorService executor = Executors.newFixedThreadPool(consumerNum);

    //Start the producer
    executor.execute(new Producer(this.buffer, studentFilePath,producerLatch,this.capacity));

    //Start the consumer
    for (int i = 0; i < consumerNum; i++) {
      executor.execute(new Consumer(this.buffer,this.data,lockTable,consumerLatch,producerLatch));
    }

    //Wait for all consumer to complete
    consumerLatch.await();
    publisher.generateFiles(this.data,fileNameSet);
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
