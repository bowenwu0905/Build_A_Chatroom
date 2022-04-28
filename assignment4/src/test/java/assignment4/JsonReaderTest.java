package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    TreeMap<String, List<String>> map = js.jsonProcess("/home/zitao/Dropbox/homework/pdp/Group_zitao_bowenwu0826_xcjiang/assignment4/src/test/java/assignment4/test.json");
    TreeMap<String, List<String>> expected = new TreeMap<>();
    expected.put("start", Arrays.asList("The <object> tonight."));
    expected.put("object",Arrays.asList("waves"));
    Assertions.assertEquals(expected, map);
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