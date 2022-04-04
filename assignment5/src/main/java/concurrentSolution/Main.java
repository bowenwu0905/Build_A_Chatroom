package concurrentSolution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
  private ConcurrentHashMap<String, ConcurrentMap<String,Integer>>data = new ConcurrentHashMap<>();
  private BlockingQueue<Integer> buffer = new LinkedBlockingQueue<>(5);
  private final int consumerNum = 5;

  public Main(){

  }

  public static void main(String[] args){


  }

  public void run(String[] args){
    BlockingQueue<Integer> buffer = new LinkedBlockingQueue<>(5);
    Thread p1  =  new Thread(new Producer(buffer));
    Publisher publisher = new Publisher(data);
    Thread c1 = new Thread(new Consumer(buffer,data));
  }


}
