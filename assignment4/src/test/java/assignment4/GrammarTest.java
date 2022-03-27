package assignment4;

import java.util.TreeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xiaochong
 */
class GrammarTest {

  private Grammar grammar;

  @BeforeEach
  void setUp() {
    grammar = new Grammar();
  }

  @Test
  void textGenerator() throws Exception {
    JsonReader jsonReader = new JsonReader();
    Assertions.assertNotNull(jsonReader.jsonProcess("./templates/insult_grammar.json"));
    Assertions.assertThrows(UndefinedTerminalException.class, () -> grammar.textGenerator("start", new TreeMap<>()));
  }

  @Test
  void numberGenerator() {
    Assertions.assertNotEquals(grammar.numberGenerator(10), 20);
  }

  @Test
  void testHashCode() {
    Grammar grammar1 = new Grammar();
    Assertions.assertNotEquals(grammar1.hashCode(), grammar.hashCode());
  }

  @Test
  void testEquals() {
    Grammar grammar1 = new Grammar();
    Assertions.assertNotEquals(grammar1, grammar);
  }

  @Test
  void testToString() {
    Grammar grammar1 = new Grammar();
    Assertions.assertEquals(grammar1.toString(), grammar.toString());
  }
}