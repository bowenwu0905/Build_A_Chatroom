package assignment4;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;
//import org.junit.Rule;
//import org.junit.contrib.java.lang.system.Assertion;
//import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserInterfaceTest {
  UserInterface ui;
  String rootPath = new File("").getAbsolutePath();
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;



  @BeforeEach
  void setUp() {
    ui = new UserInterface();
  }

  @Test
  void absolutePath() {
    String path = "";
    String x = ui.absolutePath(path);
    Assertions.assertEquals(rootPath,x);
  }

  @Test
  void setfileDictionary() {
    ui.setFileDictionary();
    ArrayList<String> a = new ArrayList<>();
    a.add("insult_grammar");
    a.add("our_own_grammar");
    a.add("poem_grammar");
    a.add("term_paper_grammar");

    Assertions.assertEquals(a, ui.getFileDictionary());
  }

  @Test
  void getFileDictionary() {
    ui.setFileDictionary();
    ArrayList<String> a = new ArrayList<>();
    a.add("insult_grammar");
    a.add("our_own_grammar");
    a.add("poem_grammar");
    a.add("term_paper_grammar");
    Assertions.assertEquals(a, ui.getFileDictionary());
  }

  @Test
  void getfileMap() {
    ui.setFileDictionary();
    Map<Integer, String> a = new HashMap<>();
    a.put(1, rootPath+"/templates/insult_grammar.json");
    a.put(2, rootPath+"/templates/our_own_grammar.json");
    a.put(3, rootPath+"/templates/poem_grammar.json");
    a.put(4, rootPath+"/templates/term_paper_grammar.json");
    Assertions.assertEquals(a, ui.getFileMap());
  }



  @Test
  void display1() {
    String input = "5"+ System.getProperty("line.separator")+"q"+ System.getProperty("line.separator");
    InputStream in = new ByteArrayInputStream(input.getBytes());
    NumberFormatException thrown2 = Assertions.assertThrows(NumberFormatException.class, () -> {
      System.setIn(in);
      ui.display();
    }, "no exception");
  Assertions.assertEquals("Input is out of range!",thrown2.getMessage());

  }


//  @Rule
//  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

  @Test
  void display2() {
    try {
      System.setOut(new PrintStream(outContent));
      String input = "q" + System.getProperty("line.separator");
      InputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);
      ui.display();
    } finally {
      System.setOut(originalOut);
    }
    Assertions.assertEquals("", outContent.toString());
  }



  @Test
  void testEquals() {
    Assertions.assertTrue(ui.equals(ui));
  }

  @Test
  void testEquals1() {
    Assertions.assertFalse(ui.equals(8));
  }

  @Test
  void testEquals2() {
    Assertions.assertFalse(ui.equals(null));
  }

  @Test
  void testEquals3() {
    UserInterface ui2 = new UserInterface();

    Assertions.assertFalse(ui.equals(ui2));
  }

  @Test
  void testHashCode() {
    Assertions.assertEquals(ui.hashCode(),ui.hashCode());

  }

  @Test
  void testHashCode1() {
    ui.setFileDictionary();
    UserInterface ui2 = new UserInterface();
    Assertions.assertFalse(ui.hashCode()==ui2.hashCode());

  }

  @Test
  void testToString() {
    String ans = "UserInterface{fileDictionary=null, fileMap=null, jsonReader=JsonReader{}, grammar=Grammar{}}";
    Assertions.assertEquals(ans,ui.toString());
  }
}