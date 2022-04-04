package concurrentSolution;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Publisher {
  private ConcurrentHashMap<String, ConcurrentMap<String,Integer>> data;

  public Publisher(
      ConcurrentHashMap<String, ConcurrentMap<String, Integer>> data) {
    this.data = data;
  }

  public ConcurrentHashMap<String, ConcurrentMap<String, Integer>> getData() {
    return data;
  }

  public void setData(
      ConcurrentHashMap<String, ConcurrentMap<String, Integer>> data) {
    this.data = data;
  }

  public void saveToCSV(){

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Publisher publisher = (Publisher) o;
    return Objects.equals(data, publisher.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }

  @Override
  public String toString() {
    return "Publisher{" +
        "data=" + data +
        '}';
  }
}
