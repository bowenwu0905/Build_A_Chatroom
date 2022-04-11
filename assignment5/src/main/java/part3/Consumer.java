package part3;


import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;


public class Consumer implements Runnable {

  private BlockingQueue<Map<String, String>> buffer;
  private CountDownLatch consumerLatch;
  private CountDownLatch producerLatch;

  private int threshold;
  private BlockingQueue<Map<String, String>> activityDays;

  public Consumer(BlockingQueue<Map<String, String>> buffer,
      CountDownLatch consumerLatch,
      CountDownLatch producerLatch,
      BlockingQueue<Map<String, String>> activityDays,
      int threshold) {
    this.buffer = buffer;
    this.consumerLatch = consumerLatch;
    this.producerLatch = producerLatch;
    this.activityDays = activityDays;
    this.threshold = threshold;
  }

  @Override
  public void run() {
    synchronized (this.buffer) {
      //Keep processing while producer still work or buffer isn't empty
      while ((producerLatch.getCount() > 0) || (!this.buffer.isEmpty())) {
        while (!this.buffer.isEmpty()) {
          try {
            Map<String, String> record = buffer.take();
            if (Integer.parseInt(record.get(FilePublisher.TOTAL_CLICK)) > threshold) {
              this.activityDays.add(record);
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
    consumerLatch.countDown();
  }


}
