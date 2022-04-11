package part3;


import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * The consumer class. It is for putting the values which larger than threshold in the queue.
 * It will keep running until the producer
 * terminates and the buffer is empty
 */
public class Consumer implements Runnable {

  private BlockingQueue<Map<String, String>> buffer;
  private CountDownLatch consumerLatch;
  private CountDownLatch producerLatch;

  private int threshold;
  private BlockingQueue<Map<String, String>> activityDays;
  /**
   * The consumer class
   *
   * @param buffer        the buffer for transferring information
   * @param consumerLatch the latch from consumers
   * @param producerLatch the latch from producers
   * @param activityDays queue store the values larger than threshold
   * @param threshold threshold
   */
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

  /**
   * get the buffer
   * @return
   */
  public BlockingQueue<Map<String, String>> getBuffer() {
    return buffer;
  }

  /**
   * get the threshold
   * @return
   */
  public int getThreshold() {
    return threshold;
  }

  /**
   * get the activity days queue
   * @return
   */
  public BlockingQueue<Map<String, String>> getActivityDays() {
    return activityDays;
  }

  /**
   * set threshold
   * @param threshold
   */
  public void setThreshold(int threshold) {
    this.threshold = threshold;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Consumer)) {
      return false;
    }
    Consumer consumer = (Consumer) o;
    return getThreshold() == consumer.getThreshold() && Objects.equals(getBuffer(),
        consumer.getBuffer()) && Objects.equals(consumerLatch, consumer.consumerLatch)
        && Objects.equals(producerLatch, consumer.producerLatch)
        && Objects.equals(getActivityDays(), consumer.getActivityDays());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBuffer(), consumerLatch, producerLatch, getThreshold(),
        getActivityDays());
  }

  @Override
  public String toString() {
    return "Consumer{" +
        "buffer=" + buffer +
        ", consumerLatch=" + consumerLatch +
        ", producerLatch=" + producerLatch +
        ", threshold=" + threshold +
        ", activityDays=" + activityDays +
        '}';
  }
}
