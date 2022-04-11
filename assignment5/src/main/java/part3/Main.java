package part3;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class Main {
  private static final int consumerNum = 5;
  private static final int producerNum = 1;
  private static final String courseFileName = "courses.csv";
  private BlockingQueue<Map<String,String>> buffer = new LinkedBlockingQueue<>();

  private BlockingQueue<Map<String, String>> activityDays = new LinkedBlockingQueue<>();
  private static final String ACTIVITY_THRESHOLD = "activity-threshold";

  public static void main(String[] args) throws InterruptedException {
    Main main = new Main();
    main.run(args);
  }

  public void run(String[] args) throws InterruptedException {
    String studentFilePath = args[1];
    String courseFilePath = args[0]+"/"+this.courseFileName;
    int threshold = Integer.parseInt(args[2]);
    FilePublisher publisher = new FilePublisher();

    Set<String> fileNameSet = publisher.fileNameGenerator(courseFilePath);

    //Generate the producer and consumer lock
    CountDownLatch producerLatch = new CountDownLatch(producerNum);
    CountDownLatch consumerLatch = new CountDownLatch(consumerNum);
    ExecutorService executor = Executors.newFixedThreadPool(consumerNum);

    //Start the producer
    executor.execute(new Producer(this.buffer, studentFilePath,producerLatch, fileNameSet));

    //Start the consumer
    for (int i = 0; i < consumerNum; i++) {
      executor.execute(new Consumer(this.buffer,consumerLatch,producerLatch, this.activityDays, threshold));
    }

    //Wait for all consumer to complete
    consumerLatch.await();
    publisher.saveFileToAddress(ACTIVITY_THRESHOLD, this.activityDays);
    executor.shutdown();
  }

}
