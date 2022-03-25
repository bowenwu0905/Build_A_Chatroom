package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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
    ui.setfileDictionary();
    ArrayList<String> a = new ArrayList<>();
    a.add("insult_grammar");
    a.add("poem_grammar");
    a.add("term_paper_grammar");
    Assertions.assertEquals(a, ui.getFileDictionary());
  }

  @Test
  void getFileDictionary() {
    ui.setfileDictionary();
    ArrayList<String> a = new ArrayList<>();
    a.add("insult_grammar");
    a.add("poem_grammar");
    a.add("term_paper_grammar");
    Assertions.assertEquals(a, ui.getFileDictionary());
  }

  @Test
  void display() {
    ui.display();
  }

}