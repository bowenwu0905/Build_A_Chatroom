package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import protocol.MessageType;
import protocol.Protocol;
import protocol.ProtocolImp;
import util.Command;

/**
 * The Handler class for pairing the command and transferring them into related protocol
 */
public class InputHandler {

  private String userName;
  private DataOutputStream toServer;
  private Protocol protocol;
  private final static int FIRST_CHARACTER = 0;
  private final static int SECOND_CHARACTER = 1;

  private final static int START_WORD_LENGTH_LOWER_BOUND = 1;
  private final static int FIRST_PART_INPUT_INDEX = 0;
  private final static int SECOND_PART_INPUT_INDEX = 1;
  private final static int DIVIDE_INTO_PARTS = 2;
  private final static String SPACE = " ";
  private final static String CASE_IGNORE = "(?i)";

  private final static String EMPTY = "";

  /**
   * The constructor for the class
   *
   * @param userName user's Name
   * @param toServer dataOutPutStream of client
   */
  public InputHandler(String userName, DataOutputStream toServer) {
    this.toServer = toServer;
    this.userName = userName;
    this.protocol = new ProtocolImp();
  }

  /**
   * Parse the input and transfer them into different message based on protocol
   *
   * @param Input the user's input
   * @throws IOException if dataOutputStream broken, then it will throw
   */
  public void inputParse(String Input) throws IOException {
    String lowerCaseInput = Input.toLowerCase();
    String firstWord = lowerCaseInput.split(SPACE, DIVIDE_INTO_PARTS)[FIRST_PART_INPUT_INDEX];
    //logOff
    if (lowerCaseInput.equals(Command.LOG_OFF)) {
      //username
      protocol.encode(MessageType.DISCONNECT_MESSAGE, Arrays.asList(userName), toServer);
    }
    //who
    else if (lowerCaseInput.equals(Command.WHO)) {
      //username
      protocol.encode(MessageType.QUERY_USERS, Arrays.asList(userName), toServer);
    } else if (lowerCaseInput.equals(Command.HELP)) {
      System.out.println(Command.HELP_MENU);
    }
    //@user
    else if (firstWord.charAt(FIRST_CHARACTER) == Command.AT_USER
        && firstWord.length() > START_WORD_LENGTH_LOWER_BOUND && !firstWord.equals(
        Command.AT_ALL)) {
      //userName
      //receiverName
      //Message
      String receiverName = firstWord.substring(SECOND_CHARACTER);
      String text = Input.replaceAll(Command.AT_USER+receiverName, EMPTY);
      protocol.encode(MessageType.DIRECT_MESSAGE, Arrays.asList(userName, receiverName, text),
          toServer);
    }
    //@insult
    else if (firstWord.charAt(FIRST_CHARACTER) == Command.INSULT_USER
        && firstWord.length() > START_WORD_LENGTH_LOWER_BOUND) {
      //userName
      //receiverName
      //Message
      String receiverName = firstWord.substring(SECOND_CHARACTER);
      protocol.encode(MessageType.SEND_INSULT, Arrays.asList(userName, receiverName), toServer);
    }
    //@all
    else {
      //userName
      //Message
      String text = Input.replaceAll(CASE_IGNORE + Command.AT_ALL, EMPTY);
      protocol.encode(MessageType.BROADCAST_MESSAGE, Arrays.asList(userName, text), toServer);
    }
  }

  /**
   * The function for sending connect message
   *
   * @throws IOException if dataOutputStream broken, then it will throw
   */
  public void connectServer() throws IOException {
    protocol.encode(MessageType.CONNECT_MESSAGE, Arrays.asList(userName), toServer);
  }

  /**
   * get the user's name
   *
   * @return user's name
   */
  public String getUserName() {
    return userName;
  }

  /**
   * check if two objects are equal
   *
   * @param o the other object
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InputHandler that = (InputHandler) o;
    return Objects.equals(userName, that.userName);
  }

  /**
   * calculate the hashcode of the object
   *
   * @return the hashcode of object
   */
  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }

  /**
   * to string
   *
   * @return the string representation
   */
  @Override
  public String toString() {
    return "InputHandler{" +
        "userName='" + userName + '\'' +
        ", toServer=" + toServer +
        '}';
  }
}
