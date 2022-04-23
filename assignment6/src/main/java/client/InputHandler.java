package client;

import java.io.DataOutputStream;
import protocol.MessageType;
import util.Command;

public class InputHandler {
  private String userName;
  private DataOutputStream toServer;
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
  }

  //TODO: check the Input cannot be Empty.
  public void inputParse(String Input){
    String lowerCaseInput = Input.toLowerCase();
    String firstWord = lowerCaseInput.split(SPACE,DIVID_INTO_PARTS)[FIRST_PART_INPUT_INDEX];
    //logOff
    if(lowerCaseInput.equals(Command.LOG_OFF)){
      //username


    }
    //who
    else if (lowerCaseInput.equals(Command.WHO)) {
      //username

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

    }
    //@insult
    else if(firstWord.charAt(FIRST_CHARACTER)==Command.INSULT_USER && firstWord.length()>START_WORD_LENGTH_LOWER_BOUND){
      //userName
      //receiverName
      //Message
      String receiverName = firstWord.substring(SECOND_CHARACTER);
      String text = Input.split(SPACE,DIVID_INTO_PARTS)[SECOND_PART_INPUT_INDEX];
    }
    //@all
    else{
      //userName
      //receiverName
      //Message

    }
  }



}
