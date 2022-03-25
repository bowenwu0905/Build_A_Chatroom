package assignment4;

import com.google.gson.Gson;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeMap;

public class JsonReader {
  private static final String[] nonRuleFields = {"grammarTitle","grammarDesc"};
  private static final String startField = "start";

  private Grammar grammar;

  public JsonReader(){
    grammar = new Grammar();
  }

  public String jsonProcessor(String path) {
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
      // close reader
      reader.close();
      return grammar.textGenerator(startField, map);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

}
