package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import protocol.MessageType;
import protocol.Protocol;
import protocol.ProtocolImp;
import util.Command;

public class InputHandler {
  private String userName;
  private DataOutputStream toServer;
  private Protocol protocal;
  private final static int FIRST_CHARACTER = 0;
  private final static int SECOND_CHARACTER = 1;

  private final static int START_WORD_LENGTH_LOWER_BOUND = 1;
  private  final static int FIRST_PART_INPUT_INDEX = 0;
  private  final static int SECOND_PART_INPUT_INDEX = 1;
  private  final static int DIVID_INTO_PARTS = 2;
  private final static String SPACE = " ";

  public InputHandler(String userName,DataOutputStream toServer){
    this.toServer = toServer;
    this.userName = userName;
    this.protocal = new ProtocolImp();
  }

  public void inputParse(String Input) throws IOException {
    String lowerCaseInput = Input.toLowerCase();
    String firstWord = lowerCaseInput.split(SPACE,DIVID_INTO_PARTS)[FIRST_PART_INPUT_INDEX];
    //logOff
    if(lowerCaseInput.equals(Command.LOG_OFF)){
      //username
      protocal.encode(MessageType.DISCONNECT_MESSAGE, Arrays.asList(userName), toServer);
    }
    //who
    else if (lowerCaseInput.equals(Command.WHO)) {
      //username
    protocal.encode(MessageType.QUERY_USERS,Arrays.asList(userName),toServer);
    }
    else if(lowerCaseInput.equals(Command.HELP)){
      System.out.println(Command.HELP_MENU);
    }
    //@user
    else if(firstWord.charAt(FIRST_CHARACTER)==Command.AT_USER && firstWord.length()>START_WORD_LENGTH_LOWER_BOUND && !firstWord.equals(Command.AT_ALL)){
      //userName
      //receiverName
      //Message
      String receiverName = firstWord.substring(SECOND_CHARACTER);
      String text = Input.split(SPACE,DIVID_INTO_PARTS)[SECOND_PART_INPUT_INDEX];
      protocal.encode(MessageType.DIRECT_MESSAGE,Arrays.asList(userName,receiverName,text),toServer);

    }
    //@insult
    else if(firstWord.charAt(FIRST_CHARACTER)==Command.INSULT_USER && firstWord.length()>START_WORD_LENGTH_LOWER_BOUND){
      //userName
      //receiverName
      //Message
      String receiverName = firstWord.substring(SECOND_CHARACTER);
      protocal.encode(MessageType.SEND_INSULT,Arrays.asList(userName,receiverName),toServer);
    }
    //@all
    else{
      //userName
      //Message
      String text = Input.replaceAll("(?i)"+Command.AT_ALL, "");
      protocal.encode(MessageType.BROADCAST_MESSAGE,Arrays.asList(userName,text),toServer);
    }
  }

  public void connectServer() throws IOException {
    protocal.encode(MessageType.CONNECT_MESSAGE,Arrays.asList(userName),toServer);
  }

  public String getUserName() {
    return userName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InputHandler that = (InputHandler) o;
    return Objects.equals(userName, that.userName) && Objects.equals(protocal, that.protocal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName,  protocal);
  }

  @Override
  public String toString() {
    return "InputHandler{" +
        "userName='" + userName + '\'' +
        ", toServer=" + toServer +
        ", protocal=" + protocal +
        '}';
  }
}
