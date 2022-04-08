package sequentialSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Processor {
  String file;

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public Processor(String file) {
    this.file = file;
  }

  public String parser(){

    return "";
  }

  public Map<String, Map<String,Integer>> process(){
    Map<String, Map<String,Integer>> ans =new HashMap<>();
    return ans;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Processor processor = (Processor) o;
    return Objects.equals(file, processor.file);
  }

  @Override
  public int hashCode() {
    return Objects.hash(file);
  }

  @Override
  public String toString() {
    return "Processor{" +
        "File='" + file + '\'' +
        '}';
  }
}
