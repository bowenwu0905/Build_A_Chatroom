package concurrentSolution;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
  private BlockingQueue<Integer> buffer;

  public Producer(BlockingQueue<Integer> buffer) {
    this.buffer =  buffer;
  }

  public String parser(){

    return "";
  }

  @Override
  public void run() {

  }
}
