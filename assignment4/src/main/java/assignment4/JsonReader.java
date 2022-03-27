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

  /**
   * The constructor
   */
  public JsonReader(){
  }

  /**
   * Reading the json and return the transferred text
   * @param path the json file path
   * @return the generated tex
   */
  public TreeMap<String, List<String>> jsonProcess(String path) {
    TreeMap<String, List<String>> map;
    try {
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get(path));
      map = gson.fromJson(reader, TreeMap.class);
      for(String field: nonRuleFields){
        if(map.containsKey(field)) {
          map.remove(field);
        }
      }
      reader.close();
      return map;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  @Override
  public String toString() {
    return "JsonReader{}";
  }
}
