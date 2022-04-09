package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.TreeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonReaderTest {
  JsonReader js;
  @BeforeEach
  void setup(){
    js = new JsonReader();
  }

  @Test
  void jsonProcess() {
    TreeMap<String, List<String>> map = js.jsonProcess("./templates/insult_grammar.json");
    Assertions.assertEquals("<adj1>", map.get("adj").get(0));
  }

  @Test
  void testToString() {
    JsonReader jsonReader2 = new JsonReader();
    Assertions.assertEquals(js.toString(), jsonReader2.toString());
  }

  @Test
  void testHashCode() {
    JsonReader jsonReader = new JsonReader();
    Assertions.assertNotEquals(js.hashCode(), jsonReader.hashCode());
  }

  @Test
  void testEquals() {
    JsonReader jsonReader = new JsonReader();
    Assertions.assertNotEquals(js, jsonReader);
  }
}