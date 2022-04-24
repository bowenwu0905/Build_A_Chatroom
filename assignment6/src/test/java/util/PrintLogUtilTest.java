package util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintLogUtilTest {

  PrintLogUtil p1;
  String userName = "u1";
  String backMessage = "b1";
  String receiverName = "r1";
  String text = "hello";
  List<String> allUsers = Arrays.asList("u1","u2","u3");
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  public void setUpStreams() {
    p1 = new PrintLogUtil();
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }


  @Test
  void errorMessage() {
  p1.errorMessage(userName,backMessage);
  assertEquals("ERROR: user: "+ userName +" Issue:"+ backMessage +"\n\n",errContent.toString());
  }

  @Test
  void successMessage() {
    p1.successMessage(userName,backMessage);
    assertEquals("Success: user: "+userName+ " message:"+backMessage+"\n\n",outContent.toString());
  }

  @Test
  void queryMessage() {
    p1.queryMessage(userName,allUsers);
    assertEquals("Success: user: "+userName+ " All online user list:"+String.join(", ", allUsers)+"\n\n",outContent.toString());
  }

  @Test
  void oneOnOneMessage() {
    p1.oneOnOneMessage(userName,receiverName,text);
    assertEquals(userName+" -> "+receiverName+ " :"+text+"\n\n",outContent.toString());
  }

  @Test
  void groupMessage() {
    p1.groupMessage(userName,text);
    assertEquals("From "+userName+" to everyone:"+text+"\n\n",outContent.toString());
  }

  @Test
  void testToString() {
    String expected = "PrintLogUtil{}";
    assertEquals(expected,p1.toString());
  }
}