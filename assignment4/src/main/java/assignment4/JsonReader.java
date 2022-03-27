package assignment4;

import com.google.gson.Gson;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Json reader file
 * @author xiaochong and zitao
 */
public class JsonReader {
  private static final String[] nonRuleFields = {"grammarTitle","grammarDesc"};
  private static final String startField = "start";

  private Grammar grammar;

  /**
   * The constructor
   */
  public JsonReader(){
    grammar = new Grammar();
  }

  /**
   * Reading the json and return the transferred text
   * @param path the json file path
   * @return the generated tex
   */
  public String jsonProcess(String path) {
    TreeMap<String, List<String>> map;
    try {
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get(path));
      map = gson.fromJson(reader, TreeMap.class);
      for(String field: nonRuleFields){
        if(map.keySet().contains(field)) {
          map.remove(field);
        }
      }
      reader.close();
      return grammar.textGenerator(startField, map);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * check if two objects are equal
   * @param o the other object
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonReader that = (JsonReader) o;
    return Objects.equals(grammar, that.grammar);
  }

  /**
   * calculate the hashcode of the object
   * @return the hashcode for object
   */
  @Override
  public int hashCode() {
    return Objects.hash(grammar);
  }

  /**
   * generate the string of the object
   * @return the string
   */
  @Override
  public String toString() {
    return "JsonReader{" +
        "grammar=" + grammar +
        '}';
  }
}
