package assignment4;

import com.google.gson.Gson;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonReader {
  private final String[] nonRuleFields = {"grammarTitle","grammarDesc"};
  private final String startField = "start";

  public JsonReader(){

  }

  public Map<String,Object> jsonToHashMap(String path) {
    Map<String, Object> map;
    try {
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get(path));
      map = gson.fromJson(reader, Map.class);
      for(String field: nonRuleFields){
        if(map.keySet().contains(field)) {
          map.remove(field);
        }
      }

//      for (Map.Entry<?, ?> entry : map.entrySet()) {
//        System.out.println(entry.getKey() + "=" + entry.getValue());
//      }

      // close reader
      reader.close();
      return map;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /*
  parser the rule and add to the hashmap(Only add the terminal rule)
 for nonterimial, adding a list of list rule)   */
  public void ruleParser(){

  }

  public Rule startRuleGenerator(Map<String, Object> map){
    Rule startRule = new Nonterminal(startField,)

  }

  public


}
