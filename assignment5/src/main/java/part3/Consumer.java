package part3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Consumer implements Runnable{
  private BlockingQueue<Integer> buffer;
  private ConcurrentHashMap<String, ConcurrentMap<String,Integer>> data;
  private int threshold;
  public Consumer(BlockingQueue<Integer> buffer, ConcurrentHashMap<String, ConcurrentMap<String,Integer>> data,int threshold ){
    this.buffer = buffer;
    this.data = data;
    this.threshold = threshold;
  }

  public Map<String, Map<String,Integer>> process(){
    Map<String, Map<String,Integer>> ans =new HashMap<>();
    return ans;
  }

  @Override
  public void run() {

  }


}
