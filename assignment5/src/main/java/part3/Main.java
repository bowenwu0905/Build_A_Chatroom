package part3;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The main class for implementation. It will maintain a blocking queue and passing between
 * consumers and a producer.
 */
public class Main {

  /**
   * The main function, which you can run
   *
   * @param args the first arg should be the folder name which you put the data. Here, you can put
   *             "data"
   *             the second arg is the output folder of assignment2
   *             the third arg is the threshold
   * @throws InterruptedException for any interruption, the exception will be thrown
   */
  public static void main(String[] args) throws InterruptedException {
    int consumerNum = 5;
    int producerNum = 1;
    String courseFileName = "courses.csv";
    BlockingQueue<Map<String,String>> buffer = new LinkedBlockingQueue<>();

    BlockingQueue<Map<String, String>> activityDays = new LinkedBlockingQueue<>();
    String ACTIVITY_THRESHOLD = "activity-threshold";

    String courseFilePath = args[0]+"/"+ courseFileName;
    String activityFilePath = args[1];
    int threshold = Integer.parseInt(args[2]);
    FilePublisher publisher = new FilePublisher();

    Set<String> fileNameSet = publisher.fileNameGenerator(courseFilePath);

    //Generate the producer and consumer lock
    CountDownLatch producerLatch = new CountDownLatch(producerNum);
    CountDownLatch consumerLatch = new CountDownLatch(consumerNum);
    ExecutorService executor = Executors.newFixedThreadPool(consumerNum);

    //Start the producer
    executor.execute(new Producer(buffer, activityFilePath,producerLatch, fileNameSet));

    //Start the consumer
    for (int i = 0; i < consumerNum; i++) {
      executor.execute(new Consumer(buffer,consumerLatch,producerLatch, activityDays, threshold));
    }

    //Wait for all consumer to complete
    consumerLatch.await();
    publisher.saveFileToAddress(ACTIVITY_THRESHOLD, activityDays);
    executor.shutdown();
  }

}
