package assignment4;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserInterfaceTest {
  UserInterface ui;

  @BeforeEach
  void setUp() {
    ui = new UserInterface();
  }

  @Test
  void absolutePath() {
    String path = "";
    String x = ui.absolutePath(path);
    Assertions.assertEquals(x, "/Users/bowenwu0826/Desktop/Group_zitao_bowenwu0826_xcjiang/assignment4");
  }

  @Test
  void setfileDictionary() {
    ui.setFileDictionary();
    ArrayList<String> a = new ArrayList<>();
    a.add("insult_grammar");
    a.add("poem_grammar");
    a.add("term_paper_grammar");
    Assertions.assertEquals(a, ui.getFileDictionary());
  }

  @Test
  void getFileDictionary() {
    ui.setFileDictionary();
    ArrayList<String> a = new ArrayList<>();
    a.add("insult_grammar");
    a.add("poem_grammar");
    a.add("term_paper_grammar");
    Assertions.assertEquals(a, ui.getFileDictionary());
  }

  @Test
  void getfileMap() {
    ui.setFileDictionary();
    Map<Integer, String> a = new HashMap<>();
    a.put(1, "/Users/bowenwu0826/Desktop/Group_zitao_bowenwu0826_xcjiang/assignment4/templates/insult_grammar.json");
    a.put(2, "/Users/bowenwu0826/Desktop/Group_zitao_bowenwu0826_xcjiang/assignment4/templates/poem_grammar.json");
    a.put(3, "/Users/bowenwu0826/Desktop/Group_zitao_bowenwu0826_xcjiang/assignment4/templates/term_paper_grammar.json");
    Assertions.assertEquals(a, ui.getFileMap());
  }

  @Test
  void display() {
    //ui.display();
  }

}