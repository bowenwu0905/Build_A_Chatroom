package assignment4;

import static org.junit.jupiter.api.Assertions.*;

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
    Assertions.assertNotNull(js.jsonProcess("./templates/insult_grammar.json"));
  }

  @Test
  void testEquals() {
    JsonReader jsonReader2 = new JsonReader();
    Assertions.assertNotEquals(js, jsonReader2);
  }

  @Test
  void testHashCode() {
    JsonReader jsonReader2 = new JsonReader();
    Assertions.assertNotEquals(js.hashCode(), jsonReader2.hashCode());
  }

  @Test
  void testToString() {
    JsonReader jsonReader2 = new JsonReader();
    Assertions.assertEquals(js.toString(), jsonReader2.toString());
  }

}